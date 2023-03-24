package com.cb.crazybuy.core

import com.cb.crazybuy.util.BLog
import java.util.*


/**
 * @Author: duke
 * @DateTime: 2023-03-23 15:18:46
 * @Description: 功能描述：「双色球」号码生成器
 *
 */
object BallRandomHelper {

    private const val COUNT_MAX_RED = 6
    private const val RANGE_MAX_RED = 33
    private const val RANGE_MAX_BLUE = 16

    fun getRangeMaxRed(): Int {
        return RANGE_MAX_RED
    }

    /**
     * 返回 1 个 [1, maxLimit] 之间的正整数
     * @param random 随机对象
     * @param maxLimit 最大数字。必须是正整数
     */
    private fun randomNum(random: Random, maxLimit: Int): String {
        if (maxLimit <= 0) {
            return "-1"
        }
        // [0, maxLimit - 1]
        val n = random.nextInt(maxLimit)
        // [1, maxLimit]
        return "${n + 1}"
    }

    /**
     * 产生 6 个不同的红球
     */
    private fun randomSixRedOrdered(random: Random): MutableList<String> {
        val set = mutableSetOf<String>()
        do {
            val n = randomNum(random, RANGE_MAX_RED)
            set.add("$n")
        } while (set.size < COUNT_MAX_RED)
        val list = set.toMutableList()
        list.sort()
        return list
    }

    /**
     * 产生 1 个篮球
     */
    private fun randomOneBlue(random: Random): String {
        return randomNum(random, RANGE_MAX_BLUE)
    }

    /**
     * 产生一组「双色球」彩票号码。6 个红球，1 个篮球。
     */
    fun buyOneGroup(): MutableList<String> {
        val random = Random()
        // 6 个红球
        val orderedList = randomSixRedOrdered(random)
        // 1 个篮球
        orderedList.add(randomOneBlue(random))
        BLog.log("BallRandomHelper.buyOnGroup() 产生一组号码：$orderedList")
        return orderedList
    }

}