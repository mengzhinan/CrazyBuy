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

        // 一等奖
        val firstCondition = hitBlueCount == 1 && hitRedCount == 6
        if (firstCondition) {
            prizeBean.winMoney = "浮动，几百万哦"
            prizeBean.winLevelDesc = "一等奖"
        }

        // 二等奖
        val twoCondition = hitBlueCount == 0 && hitRedCount == 6
        if (twoCondition) {
            prizeBean.winMoney = "浮动，几十万哦"
            prizeBean.winLevelDesc = "二等奖"
        }

        // 三等奖
        val threeCondition = hitBlueCount == 1 && hitRedCount == 5
        if (threeCondition) {
            prizeBean.winMoney = "3000 元"
            prizeBean.winLevelDesc = "三等奖"
        }

        // 四等奖
        val fourCondition1 = hitBlueCount == 1 && hitRedCount == 4
        val fourCondition2 = hitBlueCount == 0 && hitRedCount == 5
        if (fourCondition1 || fourCondition2) {
            prizeBean.winMoney = "200 元"
            prizeBean.winLevelDesc = "四等奖"
        }

        // 五等奖
        val fiveCondition1 = hitBlueCount == 1 && hitRedCount == 3
        val fiveCondition2 = hitBlueCount == 0 && hitRedCount == 4
        if (fiveCondition1 || fiveCondition2) {
            prizeBean.winMoney = "10 元"
            prizeBean.winLevelDesc = "五等奖"
        }

        // 六等奖
        if (hitBlueCount == 1) {
            if (hitRedCount in 0..2) {
                prizeBean.winMoney = "5 元"
                prizeBean.winLevelDesc = "六等奖"
            }
        }
        return prizeBean
    }


}