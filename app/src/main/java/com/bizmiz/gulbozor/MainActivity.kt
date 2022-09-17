package com.bizmiz.gulbozor

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsetsController
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bizmiz.gulbozor.core.caches.LoginHelper
import com.bizmiz.gulbozor.core.utils.networkCheck
import com.bizmiz.gulbozor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var destinationId = 0

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.notConnected.setOnClickListener {  }
        binding.btnRefresh.setOnClickListener {
            if (networkCheck(this)){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
       checkConnect()
    }

    fun checkConnect() {
        if (!networkCheck(this)){
            binding.notConnected.visibility = View.VISIBLE
            window.statusBarColor =
                ContextCompat.getColor(this, R.color.purple_500)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
              window.decorView.windowInsetsController?.setSystemBarsAppearance(
                    0,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            }
        }else{
            binding.notConnected.visibility = View.GONE
            window.statusBarColor =
                ContextCompat.getColor(this, R.color.white)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.decorView.windowInsetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            }
        }
    }


    // when you click the screen while typing in editText the keyboard will hide
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        closeKeyboard()
        return super.dispatchTouchEvent(ev)
    }

    //    override fun onBackPressed() {
//        when(destinationId){
//            0->{
//                super.onBackPressed()
//            }
//            1->{
//               startActivity(Intent(this,AddAnnounceActivity::class.java))
//                finish()
//            }
//        }
//    }
    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
    private fun checkVersion(){

    }
}