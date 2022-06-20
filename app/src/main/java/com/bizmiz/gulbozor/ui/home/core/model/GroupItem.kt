package com.bizmiz.gulbozor.ui.home.core.model

import com.bizmiz.gulbozor.core.models.AnnounceData

data class GroupItem(
    val recImage: Int? = null, val txtCategories: String? = null,
    val txtAll: String,
    val txtYouTubeTitle: String, val imgYouTube: Int,
    val othersYouTube: Int, val allPosts: String, val flowerList: List<AnnounceData>
)