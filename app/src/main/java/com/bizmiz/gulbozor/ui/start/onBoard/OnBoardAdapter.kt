package com.bizmiz.gulbozor.ui.start.onBoard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bizmiz.gulbozor.databinding.ItemBoardBinding

class OnBoardAdapter : RecyclerView.Adapter<OnBoardAdapter.ViewHolder>() {

    var nextButtonPressed: (() -> Unit)? = null
    var data = ArrayList<OnBoardData>()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    inner class ViewHolder(private val binding: ItemBoardBinding) :
        RecyclerView.ViewHolder(binding.rootView) {
        fun bindData(d: OnBoardData) {
            binding.onBoardImage.setImageResource(d.imageBoard)
            binding.onBoardTitle.text = d.titleBoard
            binding.onBoardDescription.text = d.descriptionBoard
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBoardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindData(data[position])

    override fun getItemCount(): Int = data.size
}