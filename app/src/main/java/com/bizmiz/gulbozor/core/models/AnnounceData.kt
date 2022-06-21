package com.bizmiz.gulbozor.core.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnnounceData(
    val active: Boolean,
    val allowed: Boolean,
    val categoryId: Int?,
    val cityId: Int?,
    val description: String,
    val diameter: Int,
    val flowerType: Int?,
    val height: Int,
    val image1: String?,
    val image2: String?,
    val image3: String?,
    val image4: String?,
    val image5: String?,
    val image6: String?,
    val image7: String?,
    val image8: String?,
    val myAnnounce: Boolean,
    val phoneNumber:String,
val price:Long,
val regionId:Int?,
val seller:Boolean,
val sellerId:Int,
val shopId:Int,
val title:String,
val topNumber:Int,
    val weight: Int?,
    val withFertilizer: Boolean,
    val withPot: Boolean
):Parcelable