package com.bizmiz.gulbozor.core.models.shop

data class CreateShopRequest(
    val cityId: Int?,
    val phoneNumber1: String,
    val phoneNumber2: String,
    val regionId: Int?,
    val sellerId: Int?,
    val shopName: String,
    val streetHouse: String
)