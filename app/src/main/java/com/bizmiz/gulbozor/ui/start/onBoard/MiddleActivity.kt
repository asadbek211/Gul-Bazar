package com.bizmiz.gulbozor.ui.start.onBoard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bizmiz.gulbozor.MainActivity
import com.bizmiz.gulbozor.core.caches.LoginHelper

class MiddleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoginHelper.getHelper().login = true
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}