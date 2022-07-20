package com.bizmiz.gulbozor.ui.bottom_nav.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bizmiz.gulbozor.core.models.AnnounceResponseData
import com.bizmiz.gulbozor.databinding.FlowerItemBinding
import com.bumptech.glide.Glide
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class FlowersAdapter : RecyclerView.Adapter<FlowersAdapter.Myholder>() {

    var flowersList:List<AnnounceResponseData> = listOf(
    )
       set(value) {
           field = value
           notifyDataSetChanged()
       }
    inner class Myholder(private val binding: FlowerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun populateModel(flowerListResponse: AnnounceResponseData, position: Int) {
                Glide.with(binding.root.context).load(flowerListResponse.image1)
                    .into(binding.flowerImage)
            binding.flowerName.text = flowerListResponse.title
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val date: Date = dateFormat.parse(flowerListResponse.createAt)
            binding.flowerDescription.text = date.toString()
            val df = DecimalFormat("#,###.##")
            val number = df.format(flowerListResponse.price)
            binding.flowerPrice.text = number
            binding.cardView.setOnClickListener {
                onclick.invoke(flowerListResponse)
            }
        }

    }

    private var onclick: (flowerListResponse: AnnounceResponseData) -> Unit = {}
    fun onClickListener(onclick: (flowerListResponse: AnnounceResponseData) -> Unit) {
        this.onclick = onclick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myholder {
        val flowerItemBinding =
            FlowerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Myholder(flowerItemBinding)
    }

    override fun onBindViewHolder(holder: Myholder, position: Int) {
        holder.populateModel(flowersList[position],position)
    }

    override fun getItemCount(): Int = flowersList.size
}