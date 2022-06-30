package com.bizmiz.gulbozor.ui.youtube

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.databinding.ActivityYouTubeBinding
import com.bizmiz.gulbozor.ui.bottom_nav.categories.CategoriesFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class YouTubeActivity : AppCompatActivity() {
    private val youTubeVM: YouTubeViewModel by viewModel()

    private lateinit var adapter: YouTubeAdapter
    private var _binding: ActivityYouTubeBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityYouTubeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        youTubeVM.getYouTubePage()

        adapter = YouTubeAdapter()
        binding.categoryU.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@YouTubeActivity, CategoriesFragment::class.java))
        })
        binding.youtubeRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.youtubeRecyclerView.adapter = adapter
        //loadVideos()
        windowStatus()

        announceObserve()
    }

    private fun announceObserve() {
        youTubeVM.announcePage.observe(this, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    Toast.makeText(this, "Success" + it.data, Toast.LENGTH_SHORT).show()
                    adapter.flowersList = it.data?.content!!
                }
                ResourceState.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                ResourceState.LOADING -> {

                }
            }
        })
    }

    private fun loadVideos() {
        val video1 = YoutubeData(
            "qtql45tTH2I",
            "Gullaringiz uchun kerakli bilib olasangiz yaxshi, gul parvarishi, gullarga ozuqa."
        )
        val video2 = YoutubeData("hgZlTGA8sM8", "ATIRGULDAN QALAMCHA OLISH")
        val video3 = YoutubeData("qQ_Uaq4XdqM", "Доллар гулини черинка килишни энг зор усулли")
        val video4 = YoutubeData(
            "Y-ABH50rEPE",
            "Limon parvarishi. Uyda limon daraxtini qanday etishtirish, parvarish qilish asoslari"
        )
        val video5 = YoutubeData("FtTrKkTFJfk", "Gullarni tetik qiladigan o'g'itlar")
        val video6 = YoutubeData(
            "DAVBoXG6ON4",
            "КЕМИРА ЛЮКС как\uD83D\uDE31 использовать для домашних расстений/Gulni\uD83D\uDE0D uy sharoitida parvarishlash"
        )
        val video7 = YoutubeData(
            "Fn6GOGOcjNE",
            "“Gullarni qanday qilib, tez o‘stirsa va gullatsa bo‘ladi?” Javobi qoniqtirarmikan sizni....!?"
        )
        val video8 =
            YoutubeData("XKV3-ak-Wjw", "Hona gullari borasida shaxsiy fikrim va tajribalarim!")
        val video9 = YoutubeData(
            "zmpZ6V1XchY",
            "Dunyodagi eng chiroyli va qimmatbaho gullar, bunga qaramasdan iloji yoq"
        )

        val listVideo: ArrayList<YoutubeData> = ArrayList()
        val data: ArrayList<YoutubeData> = ArrayList()
        listVideo.add(video1)
        listVideo.add(video2)
        listVideo.add(video3)
        listVideo.add(video4)
        listVideo.add(video5)
        listVideo.add(video6)
        listVideo.add(video7)
        listVideo.add(video8)
        listVideo.add(video9)

        data.addAll(listVideo)
        //adapter.flowersList=data
    }

    private fun windowStatus() {
        window.statusBarColor =
            ContextCompat.getColor(this, R.color.gray_main)

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

    override fun onDestroy() {
        super.onDestroy()

    }
}