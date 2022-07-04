package com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bizmiz.gulbozor.databinding.ItemShopsBinding

class ShopsAdapter : RecyclerView.Adapter<ShopsAdapter.ViewHolder>() {

    var data: List<ShopsData> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(private val binding: ItemShopsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun workZone(response: ShopsData, position: Int) {
            binding.nameOfShops.text = response.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bindingItem =
            ItemShopsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bindingItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.workZone(data[position], position)
    }

    override fun getItemCount(): Int = data.size
}