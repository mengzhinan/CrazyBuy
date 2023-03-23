package com.cb.crazybuy.util

import android.util.Log

/**
 * @Author: duke
 * @DateTime: 2023-03-23 19:24:08
 * @Description: 功能描述：
 *
 */
object BLog {
    private const val TAG = "bLog"

    fun log(msg: String?) {
        val m = msg?.trim()
        if (m == null || m.isEmpty()) {
            return
        }
        Log.d(TAG, m)
    }

}