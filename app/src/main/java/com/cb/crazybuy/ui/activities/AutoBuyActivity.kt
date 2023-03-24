package com.cb.crazybuy.ui.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.cb.crazybuy.R
import com.cb.crazybuy.core.BallLotteryChecker
import com.cb.crazybuy.core.BallRandomHelper
import com.cb.crazybuy.ui.widgets.BallGroupLayout
import com.cb.crazybuy.util.CommonUtil

class AutoBuyActivity : AppCompatActivity() {

    private lateinit var btnBuy: Button
    private lateinit var etMoney: EditText
    private lateinit var lotteryNumLayout: BallGroupLayout
    private lateinit var buyNumLayout: BallGroupLayout
    private lateinit var tvTotalMoney: TextView
    private lateinit var tvLevel1: TextView
    private lateinit var tvLevel2: TextView
    private lateinit var tvLevel3: TextView
    private lateinit var tvLevel4: TextView
    private lateinit var tvLevel5: TextView
    private lateinit var tvLevel6: TextView
    private lateinit var tvNotHitCount: TextView
    private lateinit var tvHitProbability: TextView

    private var totalMoney = 0
    private var level1Num = 0
    private var level2Num = 0
    private var level3Num = 0
    private var level4Num = 0
    private var level5Num = 0
    private var level6Num = 0
    private var notHitCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_buy)

        btnBuy = findViewById(R.id.btn_buy)
        etMoney = findViewById(R.id.et_buy_of_money)
        lotteryNumLayout = findViewById(R.id.lottery_ball_layout)
        buyNumLayout = findViewById(R.id.buy_ball_layout)
        tvTotalMoney = findViewById(R.id.ll_money_label_value)
        tvLevel1 = findViewById(R.id.ll_level_1_label_value)
        tvLevel2 = findViewById(R.id.ll_level_2_label_value)
        tvLevel3 = findViewById(R.id.ll_level_3_label_value)
        tvLevel4 = findViewById(R.id.ll_level_4_label_value)
        tvLevel5 = findViewById(R.id.ll_level_5_label_value)
        tvLevel6 = findViewById(R.id.ll_level_6_label_value)
        tvNotHitCount = findViewById(R.id.ll_summary_not_hit_count)
        tvHitProbability = findViewById(R.id.ll_summary_hit_probability)

        initData()
        resetCounts()

        btnBuy.setOnClickListener {
            val strMoney = etMoney.text.toString() ?: ""
            if (!CommonUtil.isValidNum(strMoney)) {
                CommonUtil.toast(this, "金额无效")
                etMoney.setText("")
                return@setOnClickListener
            }
            var intMoney = CommonUtil.stringToIntSafe(strMoney)
            if (intMoney <= 0) {
                CommonUtil.toast(this, "请输入正整数金额")
                etMoney.setText("")
                return@setOnClickListener
            }

            btnBuy.isClickable = false
            etMoney.clearFocus()
            resetCounts()

            if (intMoney % 2 != 0) {
                intMoney += 1
                etMoney.setText("$intMoney")
            }
            CommonUtil.hideKeyBoard(this, btnBuy)
            btnBuy.postDelayed({
                buyRandomNum(intMoney, etMoney)
            }, 200)
        }
    }

    private fun initData() {
        // 设置开奖号码
        lotteryNumLayout.setBalls(
            BallLotteryChecker.checkWinningNumbers(
                BallLotteryChecker.getLastLotteryNumList(
                    this
                )
            )
        )
    }

    private fun resetCounts() {
        totalMoney = 0
        level1Num = 0
        level2Num = 0
        level3Num = 0
        level4Num = 0
        level5Num = 0
        level6Num = 0
        notHitCount = 0
        tvNotHitCount.text = "未中奖次数"
        tvHitProbability.text = "中奖概率"
    }

    private fun buyRandomNum(money: Int, editText: EditText) {
        val size = money / 2
        for (index in 1..size) {
            val ballInfo = BallLotteryChecker.checkWinningNumbers(BallRandomHelper.buyOneGroup())
            buyNumLayout.setBalls(ballInfo)
            totalMoney += ballInfo.winMoneyInt
            if (ballInfo.winLevelInt == 1) {
                level1Num += 1
            } else if (ballInfo.winLevelInt == 2) {
                level2Num += 1
            } else if (ballInfo.winLevelInt == 3) {
                level3Num += 1
            } else if (ballInfo.winLevelInt == 4) {
                level4Num += 1
            } else if (ballInfo.winLevelInt == 5) {
                level5Num += 1
            } else if (ballInfo.winLevelInt == 6) {
                level6Num += 1
            } else {
                notHitCount += 1
            }

            tvTotalMoney.text = "$totalMoney"
            tvLevel1.text = "$level1Num"
            tvLevel2.text = "$level2Num"
            tvLevel3.text = "$level3Num"
            tvLevel4.text = "$level4Num"
            tvLevel5.text = "$level5Num"
            tvLevel6.text = "$level6Num"
            tvNotHitCount.text = "未中奖：$notHitCount"
            tvHitProbability.text =
                "中奖概率：$notHitCount/$size=${(size - notHitCount) * 1.0f * 100 / size}"
        }
        editText.setText("")
        btnBuy.isClickable = true
    }
}
