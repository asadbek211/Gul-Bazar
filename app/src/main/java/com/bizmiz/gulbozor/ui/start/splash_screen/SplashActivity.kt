package com.bizmiz.gulbozor.ui.start.splash_screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.bizmiz.gulbozor.BaseActivity
import com.bizmiz.gulbozor.ui.start.onBoard.SetUpActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreen()
        Handler(Looper.getMainLooper()).postDelayed({
            activityStarter(this, SetUpActivity())
            finish()
        }, 800)
    }
}