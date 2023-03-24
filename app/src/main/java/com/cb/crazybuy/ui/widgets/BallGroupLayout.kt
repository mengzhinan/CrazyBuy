package com.cb.crazybuy.ui.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.cb.crazybuy.R
import com.cb.crazybuy.core.bean.BuyNumInfo
import com.cb.crazybuy.util.GradientDrawableUtil

/**
 * @Author: duke
 * @DateTime: 2023-03-23 20:32:25
 * @Description: 功能描述：
 */
class BallGroupLayout @JvmOverloads constructor(
    context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val VIEW_COUNT = 7
    }

    init {
        orientation = HORIZONTAL
        removeAllViews()
        addTextViews()
    }

    private var buyNumInfo: BuyNumInfo? = null

    private var redDarkBG: GradientDrawable? = null
    private var redLightBG: GradientDrawable? = null
    private var blueDarkBG: GradientDrawable? = null
    private var blueLightBG: GradientDrawable? = null

    private fun initBallItemBG(width: Int) {
        if (width <= 0) {
            return
        }
        if (redDarkBG == null) {
            redDarkBG = GradientDrawableUtil.getRoundDrawable(
                width, ContextCompat.getColor(context, R.color.COLOR_FF0000_40)
            )
        }
        if (redLightBG == null) {
            redLightBG = GradientDrawableUtil.getRoundDrawable(
                width, ContextCompat.getColor(context, R.color.COLOR_FF0000)
            )
        }
        if (blueDarkBG == null) {
            blueDarkBG = GradientDrawableUtil.getRoundDrawable(
                width, ContextCompat.getColor(context, R.color.COLOR_4586F3_40)
            )
        }
        if (blueLightBG == null) {
            blueLightBG = GradientDrawableUtil.getRoundDrawable(
                width, ContextCompat.getColor(context, R.color.COLOR_4586F3)
            )
        }
    }

    private fun addTextViews() {
        for (index in 0 until VIEW_COUNT) {
            val textView = TextView(context)
            textView.text = "?"
            textView.textSize = 18f
            textView.setTextColor(Color.WHITE)
            textView.paint.isFakeBoldText = true
            textView.gravity = Gravity.CENTER
            addView(textView)
        }
    }

    fun setBalls(buyNumInfoTemp: BuyNumInfo?) {
        buyNumInfo = buyNumInfoTemp
        requestLayout()
    }

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var thisWidth = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var thisHeight = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        // 控制布局的宽高，确保每个小球都是圆形的
        if (thisWidth > thisHeight * VIEW_COUNT) {
            thisWidth = thisHeight * VIEW_COUNT
        }
        if (thisWidth / VIEW_COUNT < thisHeight) {
            thisHeight = thisWidth / VIEW_COUNT
        }

        val eachChildSide = thisHeight

        initBallItemBG(eachChildSide)

        val childCount = childCount
        if (childCount > 0) {
            for (index in 0 until childCount) {
                val tv = getChildAt(index) ?: continue
                val tvParam = tv.layoutParams ?: LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT
                )
                tvParam.width = eachChildSide
                tvParam.height = eachChildSide
            }
        }

        val newWidthSpec = MeasureSpec.makeMeasureSpec(thisWidth, widthMode)
        val newHeightSpec = MeasureSpec.makeMeasureSpec(thisHeight, heightMode)
        setMeasuredDimension(newWidthSpec, newHeightSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        updateUI()
    }

    private fun updateUI() {
        val ballList = buyNumInfo?.buyNumList
        val ballSize = ballList?.size ?: 0

        if (childCount > 0) {
            for (index in 0 until childCount) {

                val v = getChildAt(index) ?: continue
                val tv = v as? TextView ?: continue

                if (ballList == null || ballSize != VIEW_COUNT) {
                    // 无效数据，设置默认颜色
                    tv.text = "?"
                    if (index == (childCount - 1)) {
                        //最后一个，蓝色球
                        tv.background = blueDarkBG
                    } else {
                        // 红色球
                        tv.background = redDarkBG
                    }
                } else {
                    // 有效数据
                    // 设置号码文本
                    val ballInfo = ballList[index]
                    tv.text = "${ballInfo.num}"
                    if (index == childCount - 1) {
                        // 蓝色号码球
                        if (ballInfo.isHit) {
                            tv.background = blueLightBG
                        } else {
                            tv.background = blueDarkBG
                        }
                    } else {
                        // 红色号码球
                        if (ballInfo.isHit) {
                            tv.background = redLightBG
                        } else {
                            tv.background = redDarkBG
                        }
                    }
                }
            }
        }
    }

}
