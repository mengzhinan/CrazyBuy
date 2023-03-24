package com.cb.crazybuy.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.cb.crazybuy.R
import com.cb.crazybuy.core.BallLotteryChecker
import com.cb.crazybuy.ui.widgets.BallGroupLayout
import com.cb.crazybuy.util.CommonUtil

class MainActivity : AppCompatActivity() {

    private lateinit var ballLayout: BallGroupLayout

    private lateinit var btnSetLotteryNum: Button
    private lateinit var btnAutoBuy: Button
    private lateinit var btnCheckNum: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ballLayout = findViewById(R.id.ball_layout)
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

    override fun onResume() {
        super.onResume()

        // 加载上次设置的开奖号码
        val s = BallLotteryChecker.loadLastLotteryIfExists(this)
        if (s.isSuccess) {
            CommonUtil.toast(this, "默认开奖号码加载成功")
        }

        // 设置开奖号码
        ballLayout.setBalls(
            BallLotteryChecker.checkWinningNumbers(
                BallLotteryChecker.getLastLotteryNumList(
                    this
                )
            )
        )
    }

}