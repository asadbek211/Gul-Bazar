package com.bizmiz.gulbozor.core.models.youtube.getVideoLinkById

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VideoID(
    val categoryId: Int,
    val id: Int,
    val videoLink: String
) : Parcelable