package com.bizmiz.gulbozor.ui.bottom_nav.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bizmiz.gulbozor.core.models.AnnounceData
import com.bizmiz.gulbozor.databinding.FlowerItemBinding
import com.bumptech.glide.Glide
import java.text.DecimalFormat

class FlowersAdapter : RecyclerView.Adapter<FlowersAdapter.Myholder>() {

    var flowersList: List<AnnounceData> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class Myholder(private val binding: FlowerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun populateModel(flowerListResponse: AnnounceData, position: Int) {
            Glide.with(binding.root.context).load(flowerListResponse.image1)
                .into(binding.flowerImage)
            binding.flowerName.text = flowerListResponse.title
            binding.flowerDescription.text = flowerListResponse.createAt
            val df = DecimalFormat("#,###.##")
            val number = df.format(flowerListResponse.price)
            binding.flowerPrice.text = number
            binding.cardView.setOnClickListener {
                onclick.invoke(flowerListResponse)
            }
        }

    }

    private var onclick: (flowerListResponse: AnnounceData) -> Unit = {}
    fun onClickListener(onclick: (flowerListResponse: AnnounceData) -> Unit) {
        this.onclick = onclick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myholder {
        val flowerItemBinding =
            FlowerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Myholder(flowerItemBinding)
    }

    override fun onBindViewHolder(holder: Myholder, position: Int) {
        holder.populateModel(flowersList[position], position)
    }

    override fun getItemCount(): Int = flowersList.size
}