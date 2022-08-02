package com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category.oneShop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bizmiz.gulbozor.databinding.FlowerItemBinding
import com.bumptech.glide.Glide
import java.text.DecimalFormat

class OneShopAdapter : RecyclerView.Adapter<OneShopAdapter.MyViewHolder>() {
    var oneShopList = ArrayList<com.bizmiz.gulbozor.core.models.category.Content>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class MyViewHolder(private val binding: FlowerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun workWithModel(
            response: com.bizmiz.gulbozor.core.models.category.Content,
            position: Int
        ) {
            Glide.with(binding.root.context).load(response.image1)
                .into(binding.flowerImage)
            binding.flowerName.text = response.title
            binding.flowerDescription.text = response.createAt.toString()
            val df = DecimalFormat("#,###.##")
            val number = df.format(response.price)
            binding.flowerPrice.text = number
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val flowerItemBinding =
            FlowerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(flowerItemBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.workWithModel(oneShopList[position], position)
    }

    override fun getItemCount(): Int = oneShopList.size
}