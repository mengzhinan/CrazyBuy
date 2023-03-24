package com.cb.crazybuy.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.cb.crazybuy.R
import com.cb.crazybuy.core.BallLotteryChecker
import com.cb.crazybuy.util.CommonUtil

class MainActivity : AppCompatActivity() {

    private lateinit var btnSetLotteryNum: Button
    private lateinit var btnAutoBuy: Button
    private lateinit var btnCheckNum: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 加载上次设置的开奖号码
        val s = BallLotteryChecker.loadLastLotteryIfExists(this)
        if (s.isSuccess) {
            CommonUtil.toast(this, "默认开奖号码加载成功")
        }

        btnSetLotteryNum = findViewById(R.id.btn_set_lottery_num)
        btnAutoBuy = findViewById(R.id.btn_auto_buy)
        btnCheckNum = findViewById(R.id.btn_check_num)

        btnSetLotteryNum.setOnClickListener {
            startActivity(Intent(this, SetLotteryNumActivity::class.java))
        }

        btnAutoBuy.setOnClickListener {
            startActivity(Intent(this, AutoBuyActivity::class.java))
        }

        btnCheckNum.setOnClickListener {
            startActivity(Intent(this, CheckNumActivity::class.java))
        }
    }

}