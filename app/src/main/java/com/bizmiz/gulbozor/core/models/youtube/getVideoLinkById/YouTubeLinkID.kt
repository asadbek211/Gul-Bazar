package com.bizmiz.gulbozor.core.models.youtube.getVideoLinkById

import com.google.gson.annotations.SerializedName

data class YouTubeLinkID(
    val massage: Any,
    @SerializedName("object")
    val videoID: Object,
    val success: Boolean
)