package com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category.oneShop

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bizmiz.gulbozor.core.models.AnnounceResponseData
import com.bizmiz.gulbozor.databinding.FlowerItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.text.DecimalFormat

class OneShopAdapter : RecyclerView.Adapter<OneShopAdapter.MyViewHolder>() {

    var oneShopList = ArrayList<AnnounceResponseData>()
    fun addOneShopListData(response: List<AnnounceResponseData>) {
        this.oneShopList.addAll(response)
        notifyItemRangeInserted(this.oneShopList.size - response.size, response.size)
    }

    fun clearAdapter() {
        oneShopList.clear()
        notifyDataSetChanged()
    }

    inner class MyViewHolder(private val binding: FlowerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun workWithModel(
            response: AnnounceResponseData,
            position: Int
        ) {
            Glide.with(binding.root.context).load(response.image1)
                .listener(listener(binding.progressBarItem))
                .into(binding.flowerImage)
            binding.flowerName.text = response.title
            binding.flowerDescription.text = response.createAt.toString()
            val df = DecimalFormat("#,###.##")
            val number = df.format(response.price)
            binding.flowerPrice.text = number
            binding.cardView.setOnClickListener {
                onclick.invoke(response)
            }
        }
    }

    private fun listener(progressBar: ProgressBar) = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            progressBar.isVisible = false
            return false
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

    private var onclick: (flowerListResponse: AnnounceResponseData) -> Unit = {}
    fun onClickListener(onclick: (flowerListResponse: AnnounceResponseData) -> Unit) {
        this.onclick = onclick
    }

}