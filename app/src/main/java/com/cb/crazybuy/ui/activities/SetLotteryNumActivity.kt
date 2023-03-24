package com.cb.crazybuy.ui.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.cb.crazybuy.R
import com.cb.crazybuy.core.BallLotteryChecker
import com.cb.crazybuy.util.CommonUtil

class SetLotteryNumActivity : AppCompatActivity() {
    private lateinit var btnConfirm: Button
    private lateinit var etRed1: EditText
    private lateinit var etRed2: EditText
    private lateinit var etRed3: EditText
    private lateinit var etRed4: EditText
    private lateinit var etRed5: EditText
    private lateinit var etRed6: EditText
    private lateinit var etBlue: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_lottery_num)

        btnConfirm = findViewById(R.id.btn_confirm)
        etRed1 = findViewById(R.id.et_red_1)
        etRed2 = findViewById(R.id.et_red_2)
        etRed3 = findViewById(R.id.et_red_3)
        etRed4 = findViewById(R.id.et_red_4)
        etRed5 = findViewById(R.id.et_red_5)
        etRed6 = findViewById(R.id.et_red_6)
        etBlue = findViewById(R.id.et_blue)

        btnConfirm.setOnClickListener {
            val strRed1 = etRed1.text?.toString() ?: ""
            if (!CommonUtil.isValidNum(strRed1)) {
                CommonUtil.toast(this, "红球1 号码无效")
                etRed1.setText("")
                return@setOnClickListener
            }
            val strRed2 = etRed2.text?.toString() ?: ""
            if (!CommonUtil.isValidNum(strRed2)) {
                CommonUtil.toast(this, "红球2 号码无效")
                etRed2.setText("")
                return@setOnClickListener
            }
            val strRed3 = etRed3.text?.toString() ?: ""
            if (!CommonUtil.isValidNum(strRed3)) {
                CommonUtil.toast(this, "红球3 号码无效")
                etRed3.setText("")
                return@setOnClickListener
            }
            val strRed4 = etRed4.text?.toString() ?: ""
            if (!CommonUtil.isValidNum(strRed4)) {
                CommonUtil.toast(this, "红球4 号码无效")
                etRed4.setText("")
                return@setOnClickListener
            }
            val strRed5 = etRed5.text?.toString() ?: ""
            if (!CommonUtil.isValidNum(strRed5)) {
                CommonUtil.toast(this, "红球5 号码无效")
                etRed5.setText("")
                return@setOnClickListener
            }
            val strRed6 = etRed6.text?.toString() ?: ""
            if (!CommonUtil.isValidNum(strRed6)) {
                CommonUtil.toast(this, "红球6 号码无效")
                etRed6.setText("")
                return@setOnClickListener
            }
            val strBlue = etBlue.text?.toString() ?: ""
            if (!CommonUtil.isValidNum(strBlue)) {
                CommonUtil.toast(this, "篮球 号码无效")
                etBlue.setText("")
                return@setOnClickListener
            }
            val result = BallLotteryChecker.setCurrentLotteryNumber(
                this, listOf(strRed1, strRed2, strRed3, strRed4, strRed5, strRed6, strBlue)
            )
            if (result.isSuccess) {
                CommonUtil.toast(this, "开奖号码设置成功")
                CommonUtil.hideKeyBoard(this, btnConfirm)
                finish()
            } else {
                CommonUtil.toast(this, "开奖号码设置失败")
            }
        }
    }


}