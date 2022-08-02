package com.bizmiz.gulbozor.ui.youtube

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bizmiz.gulbozor.core.models.youtube.getVideoLinkPage.Content
import com.bizmiz.gulbozor.databinding.ItemYoutubeBinding
import com.bumptech.glide.Glide


class YouTubeAdapter : RecyclerView.Adapter<YouTubeAdapter.MyViewHolder>() {
    var mActivity: Activity = Activity()
    var context: Context? = null

    var youTubeList = ArrayList<Content>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun MyAdapter(activity: Activity, context: Context) {
        this.mActivity = activity
        this.context = context
    }

    inner class MyViewHolder(private val binding: ItemYoutubeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun workWithModel(response: Content, position: Int) {
            binding.youtubeTitle.text = response.title
            Glide.with(binding.imgYouTube).load(response.imageUrl).into(binding.imgYouTube)

            binding.imgYouTube.setOnClickListener {
                val intent = Intent(mActivity, VidePlayerActivity::class.java)
                intent.putExtra("videoLink", response.videoLink)
                context!!.startActivity(intent)

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

