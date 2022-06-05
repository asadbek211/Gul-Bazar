package com.bizmiz.gulbozor.ui.start.onBoard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.caches.SetUpHelper
import com.bizmiz.gulbozor.databinding.ActivitySetUpBinding
import com.bizmiz.gulbozor.ui.start.login.LoginActivity

class SetUpActivity : AppCompatActivity() {
    private lateinit var data: ArrayList<OnBoardData>
    private val adapter = OnBoardAdapter()

    private val boardOpen: Boolean = SetUpHelper.getHelper().board

    private var _binding: ActivitySetUpBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySetUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!boardOpen) {
            loadBoardData()
            windowStatus()
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.onBoardPager.adapter = adapter
        binding.dot3.attachTo(binding.onBoardPager)
        adapter.nextButtonPressed = {
        }

        binding.nextBoardBtn.setOnClickListener {
            if (binding.onBoardPager.currentItem == data.size - 1) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                binding.onBoardPager.setCurrentItem(binding.onBoardPager.currentItem + 1, true)
            }
        }

    }

    private val pagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

            if (position == data.size - 1) {
                binding.nextBoardBtn.setText(R.string.start_onBoard)
            } else {
                binding.nextBoardBtn.setText(R.string.next_onBoard)
            }

        }
    }

    private fun loadBoardData() {
        this.data = ArrayList<OnBoardData>()
        data.add(
            OnBoardData(
                imageBoard = R.drawable.v_p_1_splash,
                titleBoard = "Uyingizdan turib gullarni harid qilishingiz mumkin.",
                descriptionBoard = "GulBazar orqali gullaringizni soting va harid qiling."
            )
        )
        data.add(
            OnBoardData(
                imageBoard = R.drawable.v_p_2_splash,
                titleBoard = "O'simliklarni parvarish qilish bo'yicha video qo'llanmalar.",
                descriptionBoard = "Gulni ko'paytirish va parvarishlash bo'yicha GulBazardan o'rganing."
            )
        )
        data.add(
            OnBoardData(
                imageBoard = R.drawable.v_p_3_splash,
                titleBoard = "Uyingizdan turib xohlagancha gul soting.",
                descriptionBoard = "GulBazar orqali mahalliy bozor va export uchun gul yetishtirib sotishingiz mumkin."
            )
        )
        adapter.data = data
    }

    private fun windowStatus() {
        window.statusBarColor =
            ContextCompat.getColor(this, R.color.white)
    }

    override fun onResume() {
        super.onResume()
        binding.onBoardPager.registerOnPageChangeCallback(pagerCallback)
    }

    override fun onStop() {
        super.onStop()
        binding.onBoardPager.unregisterOnPageChangeCallback(pagerCallback)
    }


}