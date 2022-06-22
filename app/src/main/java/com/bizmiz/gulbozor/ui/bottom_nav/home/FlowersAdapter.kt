package com.bizmiz.gulbozor.ui.bottom_nav.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bizmiz.gulbozor.core.models.AnnounceData
import com.bizmiz.gulbozor.databinding.ItemFlowerBinding
import com.bumptech.glide.Glide
import java.text.DecimalFormat

class FlowersAdapter : RecyclerView.Adapter<FlowersAdapter.MyHolder>() {

    var flowersList: List<AnnounceData> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class MyHolder(private val binding: ItemFlowerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun populateModel(flowerListResponse: AnnounceData) {
            Glide.with(binding.root.context).load(flowerListResponse.image1)
                .into(binding.flowerImage)
            binding.flowerName.text = flowerListResponse.title
            binding.flowerDescription.text = flowerListResponse.description
            val df = DecimalFormat("#,###.##")
            val number = df.format(flowerListResponse.price)
            binding.flowerPrice.text = "$number"
            binding.cardView.setOnClickListener {
                 onclick.invoke(flowerListResponse)
             }
            /*binding.favourite.setOnClickListener {
                binding.favourite.setImageResource(R.drawable.ic_baseline_favorite_on_purple)
            }*/
        }

    }

    private var onclick: (flowerListResponse: AnnounceData) -> Unit = {}
    fun onClickListener(onclick: (flowerListResponse: AnnounceData) -> Unit) {
        this.onclick = onclick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val flowerItemBinding =
            ItemFlowerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyHolder(flowerItemBinding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.populateModel(flowersList[position])
    }

    override fun getItemCount(): Int = flowersList.size
}