package com.bizmiz.gulbozor.ui.youtube

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentVidePlayerBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class VideoPlayerFragment : Fragment(R.layout.fragment_vide_player) {
    private val binding by viewBinding { FragmentVidePlayerBinding.bind(it) }
    private var videoId:String? = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videoId = requireArguments().getString("videoLink")
        binding.videoPlayer.apply {
         settings.pluginState = WebSettings.PluginState.ON
            webChromeClient = WebChromeClient()
            settings.javaScriptEnabled = true
         settings.setAppCacheEnabled(true)
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            settings.builtInZoomControls = true
            settings.displayZoomControls  =true
            settings.loadsImagesAutomatically = true
            settings.setSupportZoom(true)
            settings.domStorageEnabled = true
         loadUrl("https://www.youtube.com/embed/$videoId")
        }
    }
}