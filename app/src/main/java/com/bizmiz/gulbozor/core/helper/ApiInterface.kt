package com.bizmiz.gulbozor.core.helper

import com.bizmiz.gulbozor.core.models.*
import com.bizmiz.gulbozor.ui.model.ImageResponseData
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("/announce/announceList")
    fun getAnnounce(): Call<List<AnnounceData>>

    @Headers("Content-Type:application/json")
    @POST("/announce")
    fun setAnnounce(
        @Body announceData: AnnounceData
    ): Call<AnnounceResponse>

    @Multipart
    @POST("/attachment/uploadImage")
    fun addFlowerImage(
        @Part image1: MultipartBody.Part?,
        @Part image2: MultipartBody.Part?,
        @Part image3: MultipartBody.Part?,
        @Part image4: MultipartBody.Part?,
        @Part image5: MultipartBody.Part?,
        @Part image6: MultipartBody.Part?,
        @Part image7: MultipartBody.Part?,
        @Part image8: MultipartBody.Part?
    ): Call<ImageResponseData>
    @GET("/region")
    fun getRegion(
    ): Call<RegionData>

    @GET("/city")
    fun getCity(
    ): Call<CityData>

    @GET("/category/allParentCategory")
    fun getFlowerType(
    ): Call<FlowerTypeData>
}