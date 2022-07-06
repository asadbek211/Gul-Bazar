package com.bizmiz.gulbozor

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    fun fullScreen() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    fun activityStarter(context: Context, activity: Activity) {
        startActivity(Intent(context, activity::class.java))
    }
}