package com.bizmiz.gulbozor.core.models.youtube.getVideoLinkById

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class YoutubeLinkID(
    //val massage: Any,
    @SerializedName("object")
    val videoID: VideoID,
    val success: Boolean
) : Parcelable