package com.bizmiz.gulbozor.core.helper

import com.bizmiz.gulbozor.core.models.*
import com.bizmiz.gulbozor.core.models.slideReklama.ReklamaImages
import com.bizmiz.gulbozor.core.models.youtube.getVideoLinkById.YouTubeLinkID
import com.bizmiz.gulbozor.core.models.youtube.getVideoLinkPage.YouTubeLinkPage
import com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category.ShopsListItem
import com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category.oneShop.model.OneShopData
import com.bizmiz.gulbozor.ui.model.ImageResponseData
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("/announce/announceList")
    fun getAnnounce(): Call<List<AnnounceResponseData>>

    @Headers("Content-Type:application/json")
    @POST("/announce")
    fun setAnnounce(
        @Body announceRequestData: AnnounceRequestData
    ): Call<AnnounceBaseResponse>

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
    @GET("/shop")
    fun getShopsList(): Call<List<ShopsListItem>>//todo dataType

    @GET("/category/byParentCategoryId/{parentId}")
    fun getCategoryParentByID(@Path("parentId") parentID: Int): Call<List<AnnounceResponseData>>
    @GET("/category/{id}")
    fun getFlowerTypeById(
        @Path("id")id:Int
    ): Call<BaseResponse<FlowerTypeDataItem>>

    @GET("videoLink/{id}")
    fun getVideoLinkById(@Path("id") id: Int): Call<YouTubeLinkID>

    @GET("videoLink?")
    fun getVideoLinkPage(
        @Query("page") page: Int
    ): Call<YouTubeLinkPage>

    @GET("/announce/byShop/{shopId}?")
    fun getOneShopPosts(
        @Path("shopId") shopId: Int,
        @Query("page") page: Int

    ): Call<OneShopData>

    @GET("/reklama/{id}")
    fun getReklamaId(@Path("id") id: Int): Call<ReklamaImages>
}