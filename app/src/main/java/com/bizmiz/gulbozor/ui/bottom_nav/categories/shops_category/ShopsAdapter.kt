package com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.databinding.ItemShopsBinding

class ShopsAdapter : RecyclerView.Adapter<ShopsAdapter.ViewHolder>() {

    private var lastPosition = -1
    var data: List<ShopsData> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(private val binding: ItemShopsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun workZone(response: ShopsData, position: Int) {
            if (position > lastPosition) {
                val anim: Animation =
                    AnimationUtils.loadAnimation(binding.nameOfShops.context, R.anim.slide_in_row)
                binding.nameOfShops.text = response.title
                binding.itemView.startAnimation(anim)
                lastPosition = position
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bindingItem =
            ItemShopsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bindingItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder.bindingAdapterPosition > lastPosition) {
            return holder.workZone(data[position], position)
        }
    }

    override fun getItemCount(): Int = data.size
}