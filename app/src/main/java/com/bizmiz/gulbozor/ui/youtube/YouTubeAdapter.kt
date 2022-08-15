package com.bizmiz.gulbozor.ui.youtube

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bizmiz.gulbozor.core.models.AnnounceResponseData
import com.bizmiz.gulbozor.core.models.youtube.getVideoLinkPage.Content
import com.bizmiz.gulbozor.databinding.ItemYoutubeBinding
import com.bumptech.glide.Glide


class YouTubeAdapter : RecyclerView.Adapter<YouTubeAdapter.MyViewHolder>() {
    var youTubeList = ArrayList<Content>()
    fun addYouTubeListData(response: List<Content>) {
        this.youTubeList.addAll(response)
        notifyItemRangeInserted(this.youTubeList.size - response.size, response.size)
    }

    fun clearAdapter() {
        youTubeList.clear()
        notifyDataSetChanged()
    }
    private var onclick: (videoLink: String) -> Unit = {}
    fun onClickListener(onclick: (videoLink: String) -> Unit) {
        this.onclick = onclick
    }
    inner class MyViewHolder(private val binding: ItemYoutubeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun workWithModel(response: Content, position: Int) {
            binding.youtubeTitle.text = response.title
            Glide.with(binding.imgYouTube).load(response.imageUrl).into(binding.imgYouTube)

            binding.imgYouTube.setOnClickListener {
                onclick.invoke(response.videoLink)
            }

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

