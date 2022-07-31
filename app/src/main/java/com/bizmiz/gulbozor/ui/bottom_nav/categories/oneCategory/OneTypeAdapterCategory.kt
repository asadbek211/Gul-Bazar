package com.bizmiz.gulbozor.ui.bottom_nav.categories.oneCategory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bizmiz.gulbozor.core.models.AnnounceResponseData
import com.bizmiz.gulbozor.databinding.FlowerItemBinding
import com.bumptech.glide.Glide
import java.text.DecimalFormat

class OneTypeAdapterCategory : RecyclerView.Adapter<OneTypeAdapterCategory.ViewHolder>() {
    var categoryList = ArrayList<AnnounceResponseData>()
        set(value) {
            field.addAll(value)
            notifyDataSetChanged()
        }

    fun clearAdapter() {
        categoryList.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: FlowerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun workWithModel(response: AnnounceResponseData, position: Int) {
            ///binding.youtubeTitle.text=response.videoID.categoryId.toString()
            Glide.with(binding.root.context).load(response.image1)
                .into(binding.flowerImage)
            binding.flowerName.text = response.title
            binding.flowerDescription.text = response.createAt.toString()
            val df = DecimalFormat("#,###.##")
            val number = df.format(response.price)
            binding.flowerPrice.text = number

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OneTypeAdapterCategory.ViewHolder {
        val flowerItemBinding =
            FlowerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(flowerItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.workWithModel(categoryList[position], position)
    }

    override fun getItemCount(): Int = categoryList.size
}