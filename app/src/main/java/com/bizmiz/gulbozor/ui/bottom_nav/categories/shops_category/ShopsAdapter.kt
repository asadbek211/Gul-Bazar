package com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.databinding.ItemShopsBinding

class ShopsAdapter : RecyclerView.Adapter<ShopsAdapter.ViewHolder>() {

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    private lateinit var mListener: onItemClickListener

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }


    private var lastPosition = -1

    var data: List<ShopsListItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(private val binding: ItemShopsBinding, listener: onItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        fun workZone(response: ShopsListItem, position: Int) {
            if (position > lastPosition) {
                val anim: Animation =
                    AnimationUtils.loadAnimation(binding.nameOfShops.context, R.anim.slide_in_row)
                binding.nameOfShops.text = response.shopName
                binding.itemView.startAnimation(anim)
                lastPosition = position
            }
        }

        init {
            binding.nameOfShops.setOnClickListener {
                listener.onItemClick(position = bindingAdapterPosition)//todo position
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bindingItem =
            ItemShopsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bindingItem, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder.bindingAdapterPosition > lastPosition) {
            return holder.workZone(data[position], position)
        }
    }

    override fun getItemCount(): Int = data.size
}