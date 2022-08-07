package com.bizmiz.gulbozor.ui.bottom_nav.home

import android.graphics.drawable.Drawable
import android.os.Build
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

class FlowersAdapter : RecyclerView.Adapter<FlowersAdapter.Myholder>() {
    var flowersList: ArrayList<AnnounceResponseData> = arrayListOf()
    fun addData(response: List<AnnounceResponseData>) {
        this.flowersList.addAll(response)
        notifyItemRangeInserted(this.flowersList.size - response.size, response.size)
    }

    fun clearAdapter() {
        flowersList.clear()
        notifyDataSetChanged()
    }

    var pageData: PageData? = null

    interface PageData {
        fun nextPage()
    }

    fun setPagerListener(data: PageData) {
        this.pageData = data
    }

    inner class Myholder(private val binding: FlowerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun populateModel(flowerListResponse: AnnounceResponseData, position: Int) {
//            if (position+1>=flowersList.size){
//                if (pageData!=null){
//                    pageData!!.nextPage()
//                }
//            }

            Glide.with(binding.root.context)
                .load(flowerListResponse.image1)// TODO: we need to add placeHolder but it does not sound ok(
                .listener(listener(binding.progressBarItem))
                .into(binding.flowerImage)

            binding.flowerName.text = flowerListResponse.title
            binding.flowerDescription.text =
                "${flowerListResponse.regionName}, ${flowerListResponse.cityName} ${
                    dataSettings(flowerListResponse.createAt)
                }"
            val df = DecimalFormat("#,###.##")
            val number = df.format(flowerListResponse.price)
            binding.flowerPrice.text = number.replace(".", " ").replace(",", " ")
            binding.cardView.setOnClickListener {
                onclick.invoke(flowerListResponse)
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

    fun deleteItemById(id: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            flowersList.removeIf {
                it.id == id
            }
        }
        flowersList.forEach {

      }
  }
    private var onclick: (flowerListResponse: AnnounceResponseData) -> Unit = {}
    fun onClickListener(onclick: (flowerListResponse: AnnounceResponseData) -> Unit) {
        this.onclick = onclick
    }
    //"2022-07-21"
    //"yyyy-MM-dd'T'HH:mm:ss"
    //"HH:mm"
    //"yyyy-MM-dd HH:mm"
    // postData = "Bugun ${String.format("%s", dateString)}"
    private fun dataSettings(data:Long?):String{
        var postData = ""
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val dateString = simpleDateFormat.format(data)
      val currentDataMillis = System.currentTimeMillis()
        val currentDateString = simpleDateFormat.format(currentDataMillis)
       if (dateString==currentDateString){
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