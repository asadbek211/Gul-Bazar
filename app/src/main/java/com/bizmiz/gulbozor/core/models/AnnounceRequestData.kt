package com.bizmiz.gulbozor.core.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnnounceData(
    val active: Boolean? = null,
    val allowed: Boolean? = null,
    val categoryId: Int? = null,
    val cityId: Int? = null,
    val department: Int? = null,
    val createAt: String? = null,
    val description: String? = null,
    val diameter: Int? = null,
    val height: Int? = null,
    val image1: String? = null,
    val image2: String? = null,
    val image3: String? = null,
    val image4: String? = null,
    val image5: String? = null,
    val image6: String? = null,
    val image7: String? = null,
    val image8: String? = null,
    val myAnnounce: Boolean? = null,
    val phoneNumber: String? = null,
    val price: Long? = null,
    val regionId: Int? = null,
    val seller: Boolean? = null,
    val sellerId: Int? = null,
    val shopId: Int? = null,
    val title: String? = null,
    val topNumber: Int? = null,
    val weight: Int? = null,
    val withFertilizer: Boolean? = null,
    val withPot: Boolean? = null,
    //val byParentCategoryId:List<ByParentIDItem>?=null
) : Parcelable