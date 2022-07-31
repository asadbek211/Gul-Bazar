package com.bizmiz.gulbozor

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

open class BaseActivity : AppCompatActivity() {
//    fun fullScreen() {
//        window.statusBarColor =
//            ContextCompat.getColor(this, R.color.white)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.decorView.windowInsetsController?.setSystemBarsAppearance(
//                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
//                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
//            )
//        }
//    }

    fun activityStarter(context: Context, activity: Activity) {
        startActivity(Intent(context, activity::class.java))
    }
}