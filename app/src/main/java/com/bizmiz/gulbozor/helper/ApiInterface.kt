package com.bizmiz.gulbozor.helper

import com.bizmiz.gulbozor.ui.model.AnnounceResponse
import com.bizmiz.gulbozor.ui.model.FlowerListResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("/announce/announceList")
    fun getAnnounce(
    ): Call<List<FlowerListResponse>>

    @Headers("Content-Type:application/json")
    @POST("/announce")
    fun setAnnounce(
        @Body flowerDataResponse: FlowerListResponse
    ): Call<AnnounceResponse>

    @Multipart
    @POST("/attachment/uploadSystem")
    fun addFlowerImage(
        @Part img1: MultipartBody.Part
    ): Call<Any>

    @GET("attachment/getMainAttachmentFromSystem/{imageId}")
    fun getAnnounceImageById(
        @Path("imageId")imageId:Int
    ): Call<List<FlowerListResponse>>
    @GET("https://gulbazar.herokuapp.com/attachment/open/{imageId}")
    fun getImageById(
        @Path("imageId")imageId:Int
    ): Call<ByteArray>
}