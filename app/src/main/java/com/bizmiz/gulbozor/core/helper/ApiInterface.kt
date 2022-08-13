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
        @Query("page") page: Int
    ): Call<GetAnnounceByIndexPage>

    @GET("videoLink?")
    fun getVideoLinkPage(
        @Query("page") page: Int
    ): Call<YouTubeLinkPage>

    @GET("announce/announceOfCustomer?")
    fun announceOfCustomers(
        @Query("page") page: Int
    ): Call<ByCategoryID>

    @GET("/announce/myAnnounce/{sellerId}?")
    fun getMyAnnounce(
        @Path("sellerId")sellerId:Int,
        @Query("page") page: Int
    ): Call<ByCategoryID>

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
    @PUT("/user/{id}")
    fun updateShopId(
        @Path("id") id: Int,
        @Body userEditRequest: UserEditRequest
    ): Call<Any>

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
    ): Call<ByCategoryID>

    @GET("announce/byCategory/{categoryId}?")
    fun getOneCategoryPosts(
        @Path("categoryId") categoryId: Int,
        @Query("page") page: Int
    ): Call<ByCategoryID>

    @GET("/announce/byDepartment/{departmentId}")
    fun getDepartmentId(
        @Path("departmentId") departmentId: Int,
        @Query("page") page: Int
    ): Call<ByCategoryID>


    @GET("/shop/{id}")
    fun getShopPhoneNumber(
        @Path("id") shopId: Int
    ): Call<ShopPhoneNumber>

    @GET("/user/{userId}")
    fun getUserData(
        @Path("userId") userId: Int
    ): Call<BaseResponse<UserDataResponse>>

    @GET("/sms/{key}/{id}")
    fun getSMSToken(
        @Path("key") key: Long,
       @Path("id") id:Int
    ): Call<SmsTokenResponse>

    @GET("/reklama/byPlaceNumber/{placeNumber}")
    fun getReklamaId(@Path("placeNumber") placeNumber: Int): Call<ReklamaImages>

    @Headers("Content-Type:application/json")
    @POST("/auth/checkPhoneNumber")
    fun checkPhoneNumber(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>
}