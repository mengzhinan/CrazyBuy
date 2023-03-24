package com.cb.crazybuy.core

import android.content.Context
import com.cb.crazybuy.R
import com.cb.crazybuy.core.bean.BuyNumBallHitInfo
import com.cb.crazybuy.core.bean.BuyNumInfo
import com.cb.crazybuy.core.bean.StatusBean
import com.cb.crazybuy.util.BLog
import com.cb.crazybuy.util.BaseSPUtil
import com.cb.crazybuy.util.CommonUtil

/**
 * @Author: duke
 * @DateTime: 2023-03-23 17:41:34
 * @Description: 功能描述：「双色球」号码中奖检测
 *
 */
object BallLotteryChecker {

    const val NO_NUMBER = -1

    // 开奖号码
    private val lotteryNumberList = mutableListOf<Int>()

    /**
     * 加载上次设置的开奖号码
     */
    fun loadLastLotteryIfExists(context: Context): StatusBean {
        val key = context.getString(R.string.sp_save_lottery_num)
        val str = BaseSPUtil.getString(context, key, "")
        if (str.trim().isEmpty()) {
            CommonUtil.toast(context, "未读取到默认开奖号码")
            return StatusBean(false, "failure. 未读取到默认开奖号码")
        }
        val list = str.split(",")
        if (list.size != 7) {
            CommonUtil.toast(context, "默认开奖号码无效")
            return StatusBean(false, "failure. 默认开奖号码无效")
        }
        return setCurrentLotteryNumber(context, list)
    }

    /**
     * 设置开奖号码。总共 7 个号码
     */
    fun setCurrentLotteryNumber(context: Context, list: List<String>): StatusBean {
        var strNumSP = ""
        lotteryNumberList.clear()
        var blue = 0
        for (index in list.indices) {
            try {
                val n = list[index].trim().toInt()
                if (n > BallRandomHelper.getRangeMaxRed()) {
                    return StatusBean(
                        false, "failure. args of $n is out of ${BallRandomHelper.getRangeMaxRed()}"
                    )
                }
                if (index == list.size - 1) {
                    // 篮球暂时不放进去，等红求排序完成后，最后单独放入
                    blue = n
                } else {
                    lotteryNumberList.add(n)
                    strNumSP += "$n,"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return StatusBean(false, "failure. args of $index is not a number")
            }
        }
        lotteryNumberList.sort()
        lotteryNumberList.add(blue)
        strNumSP += "$blue"
        if (lotteryNumberList.size != 7) {
            return StatusBean(false, "failure. args 参数个数应该是 7 个")
        }
        BLog.log("BallLotteryChecker.setCurrentLotteryNumber() 设置开奖号码：$lotteryNumberList")

        // 保存 sp
        val key = context.getString(R.string.sp_save_lottery_num)
        BaseSPUtil.putData(context, key, strNumSP, isCommit = true)

        return StatusBean(true, "success")
    }

    /**
     * 检查有没有中奖
     */
    fun checkWinningNumbers(buyGroupList: MutableList<Int>): BuyNumInfo {
        if (buyGroupList.size != 7) {
            return BuyNumInfo(winLevelOrFailureDesc = "failure. buyGroupList.size != 7")
        }

        // 截取 开奖 号码的红球
        val lRedList = lotteryNumberList.subList(0, lotteryNumberList.size - 1)
        // 截取 购买 号码的红球
        val bRedList = buyGroupList.subList(0, buyGroupList.size - 1)
        // 命中的红球 list
        val hitRedList = mutableListOf<BuyNumBallHitInfo>()

        val commonSet = mutableSetOf<Int>()
        commonSet.addAll(lRedList)

        // 加入购买的红球，并找出命中的红球
        for (item in bRedList) {
            if (!commonSet.add(item)) {
                // 重复了，已经存在。即命中了
                hitRedList.add(makeBuyNum(item))
            } else {
                hitRedList.add(makeBuyNum(NO_NUMBER))
            }
        }

        // set 不能加入重复数字的，如果 6*2 - set.size 即为中奖号码个数
        val hitRedCount = lRedList.size.shl(1) - commonSet.size

        val lLastItem = lotteryNumberList[lotteryNumberList.size - 1]
        val bLastItem = buyGroupList[buyGroupList.size - 1]
        val hitBlueCount = if (lLastItem == bLastItem) {
            // 最后，如果命中篮球，则加入篮球
            hitRedList.add(makeBuyNum(bLastItem))
            1
        } else {
            hitRedList.add(makeBuyNum(NO_NUMBER))
            0
        }

        val wn = BuyNumInfo()
        // 核对中奖结果
        val prizeBean = PrizeRule.getPrizeBean(hitRedCount, hitBlueCount)
        wn.hitRedCount = hitRedCount
        wn.hitBlueCount = hitBlueCount
        wn.winMoneyInt = prizeBean.winMoneyInt
        wn.winMoneyStr = prizeBean.winMoneyStr
        wn.winLevelOrFailureDesc = prizeBean.winLevelDesc
        wn.buyNumList = hitRedList

        return wn
    }

    private fun makeBuyNum(num: Int): BuyNumBallHitInfo {
        return BuyNumBallHitInfo(num, num != NO_NUMBER)
    }

}
