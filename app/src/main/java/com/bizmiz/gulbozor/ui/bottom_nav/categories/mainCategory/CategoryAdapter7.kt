package com.bizmiz.gulbozor.ui.bottom_nav.categories.mainCategory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bizmiz.gulbozor.core.models.category.ByParentIDItem
import com.bizmiz.gulbozor.databinding.ItemCategoryBinding

class CategoryAdapter7 : RecyclerView.Adapter<CategoryAdapter7.ViewHolder>() {
    var categoryList: List<ByParentIDItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    inner class ViewHolder(
        private val binding: ItemCategoryBinding,
        listener: onItemClickListenerCat
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun workWithModel(response: ByParentIDItem, position: Int) {
            if (lastPosition < position) {
                binding.childCategoryTxt.text = response.name
                lastPosition = position
            }
        }

        init {
            binding.childCategoryTxt.setOnClickListener {
                listener.onItemClick(position = bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder.bindingAdapterPosition > lastPosition) {
            return holder.workWithModel(categoryList[position], position)
        }
    }

    override fun getItemCount(): Int = categoryList.size

    interface onItemClickListenerCat {
        fun onItemClick(position: Int)
    }

    private lateinit var mListener: onItemClickListenerCat

    fun setOnItemClickListener(listener: onItemClickListenerCat) {
        mListener = listener
    }

    private var lastPosition = -1

}