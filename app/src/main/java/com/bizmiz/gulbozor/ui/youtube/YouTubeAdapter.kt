package com.bizmiz.gulbozor.ui.youtube

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bizmiz.gulbozor.core.models.youtube.getVideoLinkPage.Content
import com.bizmiz.gulbozor.databinding.ItemYoutubeBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class YouTubeAdapter : RecyclerView.Adapter<YouTubeAdapter.MyViewHolder>() {
    var youTubeList = ArrayList<Content>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    /* var data=ArrayList<Content>()
     set(value) {
         field.clear()
         field=value
         notifyDataSetChanged()
     }*/

    inner class MyViewHolder(private val binding: ItemYoutubeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun workWithModel(response: Content, position: Int) {
            binding.youtubeTitle.text = response.title
            binding.youTubeView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    val videoID = response.videoLink
                    youTubePlayer.loadVideo(videoID, 0f)
                    youTubePlayer.pause()
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YouTubeAdapter.MyViewHolder {
        val flowerItemBinding =
            ItemYoutubeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(flowerItemBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.workWithModel(youTubeList[position], position)
    }

    override fun getItemCount(): Int = youTubeList.size
}

