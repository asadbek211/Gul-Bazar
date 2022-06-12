package com.bizmiz.gulbozor.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnnounceDataResponse(
    val active: Boolean,
    val allowed: Boolean,
    val categoryId: Int,
    val description: String,
    val diameter: Int,
    val flowerType: Int,
    val height: Int,
    val image1: String?,
    val image2: String?,
    val image3: String?,
    val image4: String?,
    val image5: String?,
    val image6: String?,
    val image7: String?,
    val image8: String?,
    val imageIds: String,
    val price: Int,
    val sellerId: Int,
    val shopId: Int,
    val title: String,
    val weight: Int,
    val withFertilizer: Boolean,
    val withPot: Boolean
): Parcelable