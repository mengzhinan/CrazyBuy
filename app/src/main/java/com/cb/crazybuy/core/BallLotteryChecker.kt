package com.cb.crazybuy.core

import com.cb.crazybuy.bean.StatusBean
import com.cb.crazybuy.bean.WinningNumber
import com.cb.crazybuy.util.BLog

/**
 * @Author: duke
 * @DateTime: 2023-03-23 17:41:34
 * @Description: 功能描述：「双色球」号码中奖检测
 *
 */
object BallLotteryChecker {

    // 开奖号码
    private val lotteryNumberList = mutableListOf<Int>()

    /**
     * 设置开奖号码。总共 7 个号码
     */
    fun setCurrentLotteryNumber(vararg args: String): StatusBean {
        lotteryNumberList.clear()
        var blue = 0
        for (index in args.indices) {
            try {
                val n = args[index].trim().toInt()
                if (n > BallRandomHelper.getRangeMaxRed()) {
                    return StatusBean(
                        false, "failure. args of $n is out of ${BallRandomHelper.getRangeMaxRed()}"
                    )
                }
                if (index == args.size - 1) {
                    // 篮球暂时不放进去，等红求排序完成后，最后单独放入
                    blue = n
                } else {
                    lotteryNumberList.add(n)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return StatusBean(false, "failure. args of $index is not a number")
            }
        }
        lotteryNumberList.sort()
        lotteryNumberList.add(blue)
        if (lotteryNumberList.size != 7) {
            return StatusBean(false, "failure. args 参数个数应该是 7 个")
        }
        BLog.log("BallLotteryChecker.setCurrentLotteryNumber() 设置开奖号码：$lotteryNumberList")
        return StatusBean(true, "success")
    }

    /**
     * 检查有没有中奖
     */
    fun checkWinningNumbers(buyGroupList: MutableList<Int>): WinningNumber {
        if (buyGroupList.size != 7) {
            return WinningNumber(winLevelOrFailureDesc = "failure. buyGroupList.size != 7")
        }

        // 截取 开奖 号码的红球
        val lRedList = lotteryNumberList.subList(0, lotteryNumberList.size - 1)
        // 截取 购买 号码的红球
        val bRedList = buyGroupList.subList(0, buyGroupList.size - 1)
        // 命中的红球 list
        val hitRedList = mutableListOf<Int>()

        val commonSet = mutableSetOf<Int>()
        commonSet.addAll(lRedList)

        // 加入购买的红球，并找出命中的红球
        for (item in bRedList) {
            if (!commonSet.add(item)) {
                hitRedList.add(item)
            }
        }

        // set 不能加入重复数字的，如果 6*2 - set.size 即为中奖号码个数
        val hitRedCount = lRedList.size.shl(1) - commonSet.size
//        val hitRedCount = hitRedList.size

        // 未命中的红球，补充占位符
        while (hitRedList.size < 6) {
            hitRedList.add(-1)
        }

        val lLastItem = lotteryNumberList[lotteryNumberList.size - 1]
        val bLastItem = buyGroupList[buyGroupList.size - 1]
        val hitBlueCount = if (lLastItem == bLastItem) {
            // 最后，如果命中篮球，则加入篮球
            hitRedList.add(bLastItem)
            1
        } else {
            hitRedList.add(-1)
            0
        }

        val wn = WinningNumber()
        // 核对中奖结果
        val prizeBean = PrizeRule.getPrizeBean(hitRedCount, hitBlueCount)
        wn.hitRedCount = hitRedCount
        wn.hitBlueCount = hitBlueCount
        wn.winMoney = prizeBean.winMoney
        wn.winLevelOrFailureDesc = prizeBean.winLevelDesc
        wn.lotteryBallBKP = lotteryNumberList.toString()
        wn.buyGroupBKP = buyGroupList.toString()
        wn.hitBallBKP = hitRedList.toString()

        return wn
    }

}
