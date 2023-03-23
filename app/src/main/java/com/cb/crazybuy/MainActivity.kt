package com.cb.crazybuy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cb.crazybuy.core.BallLotteryChecker
import com.cb.crazybuy.core.BallRandomHelper
import com.cb.crazybuy.util.BLog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()


        BallLotteryChecker.setCurrentLotteryNumber("9","2","3","4","05 "," 12","1")

        val wn = BallLotteryChecker.checkWinningNumbers(BallRandomHelper.buyOnGroup())
        val r = wn.hitRedCount
        val b = wn.hitBlueCount
        val l = wn.winMoney
        BLog.log("r = $r")
        BLog.log("b = $b")
        BLog.log("l = $l")

    }
}