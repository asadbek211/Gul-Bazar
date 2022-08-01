package com.bizmiz.gulbozor.core.helper

import com.bizmiz.gulbozor.core.models.*
import com.bizmiz.gulbozor.core.models.category.ByParentIDItem
import com.bizmiz.gulbozor.core.models.shop.CreateShopRequest
import com.bizmiz.gulbozor.core.models.slideReklama.ReklamaImages
import com.bizmiz.gulbozor.core.models.sms.SmsResponseData
import com.bizmiz.gulbozor.core.models.user.UserDataResponse
import com.bizmiz.gulbozor.core.models.youtube.getVideoLinkById.YouTubeLinkID
import com.bizmiz.gulbozor.core.models.youtube.getVideoLinkPage.YouTubeLinkPage
import com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category.ShopsListItem
import com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category.oneShop.model.OneShopData
import com.bizmiz.gulbozor.ui.model.ImageResponseData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("/announce/indexPage?")
    fun getAnnounce(
        @Query("page") page: Int
    ): Call<AnnounceResponseData>

    @GET("videoLink?")
    fun getVideoLinkPage(
        @Query("page") page: Int
    ): Call<YouTubeLinkPage>


    @Headers("Content-Type:application/json")
    @DELETE("/announce/delete/{id}")
    fun deleteAnnounceById(
        @Path("id") announceId: Int
    ): Call<Any>

    @Headers("Content-Type:application/json")
    @POST("/announce")
    fun setAnnounce(
        @Body announceRequestData: AnnounceRequestData
    ): Call<AnnounceBaseResponse>

    @Headers("Content-Type:application/json")
    @POST("/shop")
    fun createShop(
        @Body createShopRequest: CreateShopRequest
    ): Call<BaseResponse<CreateShopRequest>>

    @Headers("Content-Type:application/json")
    @FormUrlEncoded
    @PATCH("/user/{id}")
    fun updateShopId(
        @Path("id") id: Int,
        @Field("shopId") shopId: Int
    ): Call<BaseResponse<Any>>

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

   @Multipart
    @POST
    fun smsSend(
        @Url url:String,
        @Header("Authorization") token: String,
        @Part("body") body:RequestBody,
    ): Call<SmsResponseData>

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
    fun getShopsList(): Call<List<ShopsListItem>>

    @GET("/category/byParentCategoryId/{parentId}")
    fun getCategoryParentByID(@Path("parentId") parentID: Int): Call<List<ByParentIDItem>>

    @GET("/category/{id}")
    fun getFlowerTypeById(
        @Path("id")id:Int
    ): Call<BaseResponse<FlowerTypeDataItem>>

    @GET("videoLink/{id}")
    fun getVideoLinkById(@Path("id") id: Int): Call<YouTubeLinkID>


    @GET("/announce/byShop/{shopId}?")
    fun getOneShopPosts(
        @Path("shopId") shopId: Int,
        @Query("page") page: Int
    ): Call<OneShopData>

    @GET("/user/{userId}")
    fun getUserData(
        @Path("userId") userId: Int
    ): Call<BaseResponse<UserDataResponse>>

    @GET("/reklama/byPlaceNumber/{placeNumber}")
    fun getReklamaId(@Path("placeNumber") placeNumber: Int): Call<ReklamaImages>
}