package com.bizmiz.gulbozor.core.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FlowerListResponse(
    val active: Boolean,
    val allowed: Boolean,
    val createAt: String,
    val description: String,
    val diameter: Int,
    val height: Int,
    val mainAttachId: Int,
    val price: Int,
    val title: String,
    val weight: Int,
    val withFertilizer: Boolean,
    val withPot: Boolean
):Parcelable