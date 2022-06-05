package com.bizmiz.gulbozor.ui.start.login

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bizmiz.gulbozor.MainActivity
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.caches.SetUpHelper
import com.bizmiz.gulbozor.databinding.ActivityLoginBinding
import com.bizmiz.gulbozor.ui.start.signUp.SignUpActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private var mIsShowPass = false

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SetUpHelper.getHelper().board = true

        setListeners()
        showPassword(mIsShowPass)
        checkUser()
    }

    private fun checkUser() {
        binding.startLogin.setOnClickListener(View.OnClickListener {
            if (binding.etPhoneNumber.rawText == "000000000" && binding.etPass.text.contains("00000")) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else if (binding.etPhoneNumber.rawText.length <= 8) {
                Toast.makeText(this, "Check your number", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Password Error", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun setListeners() {
        binding.signUp.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        })
        ivShowHidePass.setOnClickListener {
            mIsShowPass = !mIsShowPass
            showPassword(mIsShowPass)
        }
    }

    private fun showPassword(isShow: Boolean) {
        if (isShow) {
            binding.etPass.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.ivShowHidePass.setImageResource(R.drawable.visibility_off)
        } else {
            etPass.transformationMethod = PasswordTransformationMethod.getInstance()
            ivShowHidePass.setImageResource(R.drawable.visibility_on)
        }
        etPass.setSelection(etPass.text.toString().length)
    }

    // when you click the screen while typing in editText the keyboard will hide
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        closeKeyboard()
        return super.dispatchTouchEvent(ev)
    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}