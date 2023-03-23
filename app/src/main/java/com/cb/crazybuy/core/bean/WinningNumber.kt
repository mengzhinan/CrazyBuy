package com.cb.crazybuy.core.bean

/**
 * @Author: duke
 * @DateTime: 2023-03-23 18:29:50
 * @Description: 功能描述：
 *
 */
data class WinningNumber @JvmOverloads constructor(
    var hitRedCount: Int = 0,
    var hitBlueCount: Int = 0,
    var winMoney: String? = null,
    var winLevelOrFailureDesc: String? = null,
    var lotteryBallBKP: String? = null,
    var buyGroupBKP: String? = null,
    var hitBallBKP: String? = null
)