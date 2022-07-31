package com.bizmiz.gulbozor.ui.start.authentication.signUp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.WindowInsetsController
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.caches.LoginHelper
import com.bizmiz.gulbozor.core.caches.SetUpHelper
import com.bizmiz.gulbozor.databinding.ActivitySignUpBinding
import com.bizmiz.gulbozor.ui.start.onBoard.MiddleActivity

class SignUpActivity : AppCompatActivity() {
    private var _binding: ActivitySignUpBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SetUpHelper.getHelper().board = true
//        LoginHelper.getHelper().login = true
        if (!LoginHelper.getHelper().login) {

        } else {
            val intent = Intent(this, MiddleActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

//    // when you click the screen while typing in editText the keyboard will hide
//    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
//        closeKeyboard()
//        return super.dispatchTouchEvent(ev)
//    }
//
//    private fun closeKeyboard() {
//        val view = this.currentFocus
//        if (view != null) {
//            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.hideSoftInputFromWindow(view.windowToken, 0)
//        }
//    }
}