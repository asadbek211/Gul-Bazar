package com.bizmiz.gulbozor.core.models.category

data class Content(
    val active: Boolean,
    val allowed: Boolean,
    val callingCount: Any,
    val categoryId: Int,
    val cityId: Int,
    val cityName: String,
    val createAt: Long,
    val customer: Boolean,
    val department: Int?,
    val description: String,
    val diameter: Int,
    val height: Int,
    val id: Int,
    val image1: String,
    val image2: String,
    val image3: Any,
    val image4: Any,
    val image5: Any,
    val image6: Any,
    val image7: Any,
    val image8: Any,
    val phoneNumber: String,
    val price: Int,
    val regionId: Int,
    val regionName: String,
    val sellerId: Int,
    val shopId: Any,
    val title: String,
    val topNumber: Int,
    val weight: Int,
    val withFertilizer: Boolean,
    val withPot: Boolean
)