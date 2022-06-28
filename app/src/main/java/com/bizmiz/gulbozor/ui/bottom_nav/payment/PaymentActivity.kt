package com.bizmiz.gulbozor.ui.bottom_nav.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bizmiz.gulbozor.core.models.AnnounceResponseData
import com.bizmiz.gulbozor.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {
    private var _binding: ActivityPaymentBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
    fun getData(): AnnounceResponseData {
        return intent.extras?.get("flowerData") as AnnounceResponseData
    }
}