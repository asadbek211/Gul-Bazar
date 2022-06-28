package com.bizmiz.gulbozor.ui.start.splash_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.navigation.Navigation
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.databinding.ActivitySetUpBinding
import com.bizmiz.gulbozor.databinding.ActivitySignUpBinding
import com.bizmiz.gulbozor.databinding.ActivitySplashBinding
import com.bizmiz.gulbozor.ui.start.authentication.signUp.SignUpActivity
import com.bizmiz.gulbozor.ui.start.onBoard.SetUpActivity

class SplashActivity : AppCompatActivity() {
    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
       window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        Handler().postDelayed({
            val intent = Intent(this, SetUpActivity::class.java)
            startActivity(intent)
            finish()
        }, 800)
    }
}