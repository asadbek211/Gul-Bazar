package com.bizmiz.gulbozor.ui.bottom_nav.add

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.bizmiz.gulbozor.MainActivity
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.caches.LoginHelper
import com.bizmiz.gulbozor.databinding.ActivityAddAnnounceBinding
import com.bizmiz.gulbozor.databinding.ActivityMainBinding

class AddAnnounceActivity : AppCompatActivity() {
    private var _binding: ActivityAddAnnounceBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor =
            ContextCompat.getColor(this, R.color.gray_main)
        _binding = ActivityAddAnnounceBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}