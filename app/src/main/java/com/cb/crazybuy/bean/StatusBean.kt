package com.cb.crazybuy.bean

/**
 * @Author: duke
 * @DateTime: 2023-03-23 17:48:15
 * @Description: 功能描述：
 */
data class StatusBean @JvmOverloads constructor(
    var isSuccess: Boolean = false, var reason: String? = null
)