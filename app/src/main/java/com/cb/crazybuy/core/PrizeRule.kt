package com.cb.crazybuy.core

import com.cb.crazybuy.core.bean.PrizeBean

/**
 * @Author: duke
 * @DateTime: 2023-03-23 19:47:11
 * @Description: 功能描述：
 *
 */
object PrizeRule {

    fun getPrizeBean(hitRedCount: Int, hitBlueCount: Int): PrizeBean {
        val prizeBean = PrizeBean()
        prizeBean.hitRedCount = hitRedCount
        prizeBean.hitBlueCount = hitBlueCount
        prizeBean.winMoneyInt = 0
        prizeBean.winMoneyStr = "无"
        prizeBean.winLevelDesc = "没有中奖"

        // 注意判断顺序，满足一等奖的号码，也会满足二三四五六等奖
        // 或者加 return，中了大奖就直接返回

        // 一等奖
        val firstCondition = hitBlueCount == 1 && hitRedCount == 6
        if (firstCondition) {
            prizeBean.winMoneyInt = 5_000_000
            prizeBean.winMoneyStr = "浮动，几百万哦"
            prizeBean.winLevelDesc = "一等奖"
            return prizeBean
        }

        // 二等奖
        val twoCondition = hitBlueCount == 0 && hitRedCount == 6
        if (twoCondition) {
            prizeBean.winMoneyInt = 200_000
            prizeBean.winMoneyStr = "浮动，几十万哦"
            prizeBean.winLevelDesc = "二等奖"
            return prizeBean
        }

        // 三等奖
        val threeCondition = hitBlueCount == 1 && hitRedCount == 5
        if (threeCondition) {
            prizeBean.winMoneyInt = 3000
            prizeBean.winMoneyStr = "3000 元"
            prizeBean.winLevelDesc = "三等奖"
            return prizeBean
        }

        // 四等奖
        val fourCondition1 = hitBlueCount == 1 && hitRedCount == 4
        val fourCondition2 = hitBlueCount == 0 && hitRedCount == 5
        if (fourCondition1 || fourCondition2) {
            prizeBean.winMoneyInt = 200
            prizeBean.winMoneyStr = "200 元"
            prizeBean.winLevelDesc = "四等奖"
            return prizeBean
        }

        // 五等奖
        val fiveCondition1 = hitBlueCount == 1 && hitRedCount == 3
        val fiveCondition2 = hitBlueCount == 0 && hitRedCount == 4
        if (fiveCondition1 || fiveCondition2) {
            prizeBean.winMoneyInt = 10
            prizeBean.winMoneyStr = "10 元"
            prizeBean.winLevelDesc = "五等奖"
            return prizeBean
        }

        // 六等奖
        if (hitBlueCount == 1) {
            if (hitRedCount in 0..2) {
                prizeBean.winMoneyInt = 5
                prizeBean.winMoneyStr = "5 元"
                prizeBean.winLevelDesc = "六等奖"
                return prizeBean
            }
        }
        return prizeBean
    }


}