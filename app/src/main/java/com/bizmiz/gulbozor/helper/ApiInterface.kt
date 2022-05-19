package com.bizmiz.gulbozor.helper

import com.bizmiz.gulbozor.ui.model.FlowerDataResponse
import com.bizmiz.gulbozor.ui.model.FlowerUploadDataResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    //    @GET("/announce")
//    fun getArticles(
//        @Path("pathName") pathName: String
//    ): Call<List<ArticlesData>>
    @Headers("Content-Type:application/json")
    @POST("api/login")
    fun addFlowerData(
        @Body flowerDataResponse: FlowerDataResponse
    ): Call<FlowerUploadDataResponse>

    @Multipart
    @POST("attachment/uploadSystem")
    fun addFlowerImage(
        @Part img1: MultipartBody.Part
    ): Call<Int>
}