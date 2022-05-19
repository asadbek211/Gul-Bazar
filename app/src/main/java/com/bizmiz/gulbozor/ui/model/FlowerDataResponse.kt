package com.bizmiz.gulbozor.ui.model

data class FlowerDataResponse(
    val active:Boolean,
    val allowed:Boolean,
    val description:String,
    val diameter:Double,
    val flowerType:Long,
    val height:Double,
    val mainAttachId:Long,
    val price:Double,
    val title:String,
    val weight:Double,
    val withFertilizer:Boolean,
    val withPot:Boolean
)
