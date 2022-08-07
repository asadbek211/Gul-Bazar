package com.bizmiz.gulbozor.ui.bottom_nav.categories.oneCategory

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bizmiz.gulbozor.core.models.AnnounceResponseData
import com.bizmiz.gulbozor.core.utils.checkMonth
import com.bizmiz.gulbozor.databinding.FlowerItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class OneTypeAdapterCategory : RecyclerView.Adapter<OneTypeAdapterCategory.ViewHolder>() {


    var categoryList = ArrayList<AnnounceResponseData>()

    fun addOneCategoryListData(response: List<AnnounceResponseData>) {
        this.categoryList.addAll(response)
        notifyItemRangeInserted(this.categoryList.size - response.size, response.size)
    }

    fun clearAdapter() {
        categoryList.clear()
        notifyDataSetChanged()
    }
    /* fun clearAdapter() {
         categoryList.clear()
         notifyDataSetChanged()
     }*/

    inner class ViewHolder(private val binding: FlowerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun workWithModel(
            response: AnnounceResponseData,
            position: Int
        ) {
            ///binding.youtubeTitle.text=response.videoID.categoryId.toString()
            Glide.with(binding.root.context).load(response.image1)
                .listener(listener(binding.progressBarItem))
                .into(binding.flowerImage)
            binding.flowerName.text = response.title
            binding.flowerDescription.text =
                "${response.regionName}, ${response.cityName} ${dataSettings(response.createAt)}"
            val df = DecimalFormat("#,###.##")
            val number = df.format(response.price)
            binding.flowerPrice.text = number.replace(".", " ").replace(",", " ")
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

    private var onclick: (flowerListResponse: AnnounceResponseData) -> Unit =
        {}

    fun onClickListener(onclick: (flowerListResponse: AnnounceResponseData) -> Unit) {
        this.onclick = onclick
    }

    private fun dataSettings(data: Long?): String {
        var postData = ""
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val dateString = simpleDateFormat.format(data)
        val currentDataMillis = System.currentTimeMillis()
        val currentDateString = simpleDateFormat.format(currentDataMillis)
        if (dateString == currentDateString) {
            val timeFormat = SimpleDateFormat("HH:mm")
            val timeString = timeFormat.format(data)
            postData = "Bugun ${String.format("%s", timeString)}"
        }
        else if (currentDateString.substring(8,10).toInt()-1==dateString.substring(8,10).toInt() &&
            currentDateString.substring(5,7)==dateString.substring(5,7) &&
            currentDateString.substring(0,4)==dateString.substring(0,4)){
            val timeFormat = SimpleDateFormat("HH:mm")
            val timeString = timeFormat.format(data)
            postData = "Kecha ${String.format("%s", timeString)}"
        }else{
            val number = dateString.substring(5,7).toInt()
            postData = "${dateString.substring(8,10).toInt()}-${checkMonth(number)}"
        }
        return postData
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OneTypeAdapterCategory.ViewHolder {
        val flowerItemBinding =
            FlowerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(flowerItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.workWithModel(categoryList[position], position)
    }

    override fun getItemCount(): Int = categoryList.size
}