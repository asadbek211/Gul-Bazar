package com.bizmiz.gulbozor.core.helper

import com.bizmiz.gulbozor.core.models.*
import com.bizmiz.gulbozor.core.models.category.ByCategoryID
import com.bizmiz.gulbozor.core.models.category.ByParentIDItem
import com.bizmiz.gulbozor.core.models.home.GetAnnounceByIndexPage
import com.bizmiz.gulbozor.core.models.shop.CreateShopRequest
import com.bizmiz.gulbozor.core.models.slideReklama.ReklamaImages
import com.bizmiz.gulbozor.core.models.sms.SmsTokenResponse
import com.bizmiz.gulbozor.core.models.user.UserDataResponse
import com.bizmiz.gulbozor.core.models.youtube.getVideoLinkById.YouTubeLinkID
import com.bizmiz.gulbozor.core.models.youtube.getVideoLinkPage.YouTubeLinkPage
import com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category.ShopsListItem
import com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category.oneShop.model.ShopPhoneNumber
import com.bizmiz.gulbozor.ui.model.ImageResponseData
import com.bizmiz.gulbozor.core.models.LoginRequest
import com.bizmiz.gulbozor.core.models.user.edit_user.UserEditRequest
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("/announce/indexPage?")
    fun getAnnounce(
        @Header("Authorization") token: String,
        @Query("page") page: Int
    ): Call<GetAnnounceByIndexPage>

    @GET("videoLink?")
    fun getVideoLinkPage(
        @Header("Authorization") token: String,
        @Query("page") page: Int
    ): Call<YouTubeLinkPage>

    @GET("announce/announceOfCustomer?")
    fun announceOfCustomers(
        @Header("Authorization") token: String,
        @Query("page") page: Int
    ): Call<ByCategoryID>

    @GET("/announce/myAnnounce/{sellerId}?")
    fun getMyAnnounce(
        @Header("Authorization") token: String,
        @Path("sellerId")sellerId:Int,
        @Query("page") page: Int
    ): Call<ByCategoryID>

    @Headers("Content-Type:application/json")
    @DELETE("/announce/delete/id/{id}")
    fun deleteAnnounceById(
        @Header("Authorization") token: String,
        @Path("id") announceId: Int
    ): Call<Any>

    @Headers("Content-Type:application/json")
    @POST("/announce")
    fun setAnnounce(
        @Header("Authorization") token: String,
        @Body announceRequestData: AnnounceRequestData
    ): Call<AnnounceBaseResponse>

    @Headers("Content-Type:application/json")
    @POST("/shop")
    fun createShop(
        @Header("Authorization") token: String,
        @Body createShopRequest: CreateShopRequest
    ): Call<BaseResponse<CreateShopRequest>>

    @Headers("Content-Type:application/json")
    @PUT("/user/{id}")
    fun updateShopId(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body userEditRequest: UserEditRequest
    ): Call<Any>

    @Multipart
    @POST("/attachment/uploadImage")
    fun addFlowerImage(
        @Header("Authorization") token: String,
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
        @Header("Authorization") token: String,
    ): Call<RegionData>

    @GET("/city")
    fun getCity(
        @Header("Authorization") token: String,
    ): Call<CityData>

    @GET("/category/allParentCategory")
    fun getFlowerType(
        @Header("Authorization") token: String,
    ): Call<FlowerTypeData>

    @GET("/shop")
    fun getShopsList(
        @Header("Authorization") token: String,
    ): Call<List<ShopsListItem>>


    @GET("/category/byParentCategoryId/{parentId}")
    fun getCategoryParentByID(
        @Header("Authorization") token: String,
        @Path("parentId") parentID: Int): Call<List<ByParentIDItem>>

    @GET("/category/{id}")
    fun getFlowerTypeById(
        @Header("Authorization") token: String,
        @Path("id")id:Int
    ): Call<BaseResponse<FlowerTypeDataItem>>

    @GET("videoLink/{id}")
    fun getVideoLinkById(
        @Header("Authorization") token: String,
        @Path("id") id: Int): Call<YouTubeLinkID>


    @GET("/announce/byShop/{shopId}?")
    fun getOneShopPosts(
        @Header("Authorization") token: String,
        @Path("shopId") shopId: Int,
        @Query("page") page: Int
    ): Call<ByCategoryID>

    @GET("announce/byCategory/{categoryId}?")
    fun getOneCategoryPosts(
        @Header("Authorization") token: String,
        @Path("categoryId") categoryId: Int,
        @Query("page") page: Int
    ): Call<ByCategoryID>

    @GET("/announce/byDepartment/{departmentId}")
    fun getDepartmentId(
        @Header("Authorization") token: String,
        @Path("departmentId") departmentId: Int,
        @Query("page") page: Int
    ): Call<ByCategoryID>


    @GET("/shop/{id}")
    fun getShopPhoneNumber(
        @Header("Authorization") token: String,
        @Path("id") shopId: Int
    ): Call<ShopPhoneNumber>

    @GET("/user/{userId}")
    fun getUserData(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int
    ): Call<BaseResponse<UserDataResponse>>

    @GET("/sms/{key}/{id}")
    fun getSMSToken(
        @Path("key") key: Long,
       @Path("id") id:Int
    ): Call<SmsTokenResponse>

    @GET("/reklama/byPlaceNumber/{placeNumber}")
    fun getReklamaId(
        @Header("Authorization") token: String,
        @Path("placeNumber") placeNumber: Int): Call<ReklamaImages>

    @Headers("Content-Type:application/json")
    @POST("/auth/checkPhoneNumber")
    fun checkPhoneNumber(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>
}