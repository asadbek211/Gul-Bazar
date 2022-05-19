package com.bizmiz.gulbozor.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bizmiz.gulbozor.databinding.FlowerItemBinding

class FlowersAdapter : RecyclerView.Adapter<FlowersAdapter.Myholder>() {
    inner class Myholder(private val binding: FlowerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun populateModel() {

        }

    }

//    private var onclick: (articlesData: ArticlesData) -> Unit = {}
//    fun onClickListener(onclick: (articlesData: ArticlesData) -> Unit) {
//        this.onclick = onclick
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myholder {
        val flowerItemBinding =
            FlowerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Myholder(flowerItemBinding)
    }

    override fun onBindViewHolder(holder: Myholder, position: Int) {
        holder.populateModel()
    }

    override fun getItemCount(): Int = 10
}