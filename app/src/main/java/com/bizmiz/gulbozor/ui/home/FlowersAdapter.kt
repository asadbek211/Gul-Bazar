package com.bizmiz.gulbozor.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.models.FlowerListResponse
import com.bizmiz.gulbozor.databinding.FlowerItemBinding
import com.bumptech.glide.Glide
import java.text.DecimalFormat

class FlowersAdapter : RecyclerView.Adapter<FlowersAdapter.Myholder>() {

    var flowersList: List<FlowerListResponse> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class Myholder(private val binding: FlowerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun populateModel(flowerListResponse: FlowerListResponse) {
            Glide.with(binding.flowerImage)
                .load("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%3Fid%3DOIP.oBzEnEHYwvS8CZ1TkNC4owHaHa%26pid%3DApi&f=1")
                .into(binding.flowerImage)
            binding.flowerImage.setImageResource(R.drawable.ic_gulbazar_logo)
            binding.flowerName.text = flowerListResponse.title
            binding.flowerDescription.text = flowerListResponse.description
            val df = DecimalFormat("#,###.##")
            val number = df.format(flowerListResponse.price)
            binding.flowerPrice.text = "$$number"
            binding.cardView.setOnClickListener {
                onclick.invoke(flowerListResponse)
            }
            binding.favourite.setOnClickListener {
                binding.favourite.setImageResource(R.drawable.ic_baseline_favorite_on_purple)
            }
        }

    }

    private var onclick: (flowerListResponse: FlowerListResponse) -> Unit = {}
    fun onClickListener(onclick: (flowerListResponse: FlowerListResponse) -> Unit) {
        this.onclick = onclick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myholder {
        val flowerItemBinding =
            FlowerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Myholder(flowerItemBinding)
    }

    override fun onBindViewHolder(holder: Myholder, position: Int) {
        holder.populateModel(flowersList[position])
    }

    override fun getItemCount(): Int = flowersList.size
}