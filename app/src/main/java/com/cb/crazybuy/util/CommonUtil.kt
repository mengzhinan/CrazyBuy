package com.cb.crazybuy.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

/**
 * @Author: duke
 * @DateTime: 2023-03-23 20:22:36
 * @Description: 功能描述：
 *
 */
object CommonUtil {

    fun toast(context: Context, msg: String?) {
        msg ?: return
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun isValidNum(str: String?): Boolean {
        val s = str?.trim() ?: ""
        return try {
            s.toInt()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun isValidNum(num: Int): Boolean {
        return num in 1..33
    }

    fun stringToIntSafe(str: String?): Int {
        if (!isValidNum(str)) {
            return -1
        }
        return str?.toInt() ?: 0
    }

    fun hideKeyBoard(context: Context, view: View) {
        val imm =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager ?: return
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}