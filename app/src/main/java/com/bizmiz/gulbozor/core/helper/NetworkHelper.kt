package com.bizmiz.gulbozor.core.helper

import android.util.Log
import com.bizmiz.gulbozor.core.caches.AppCache
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
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException


class NetworkHelper(
    private val apiClient: Retrofit
) {
    fun addFlowerImage(
        img1: MultipartBody.Part?,
        img2: MultipartBody.Part?,
        img3: MultipartBody.Part?,
        img4: MultipartBody.Part?,
        img5: MultipartBody.Part?,
        img6: MultipartBody.Part?,
        img7: MultipartBody.Part?,
        img8: MultipartBody.Part?,
        onSuccess: (data: ImageResponseData) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java)
            .addFlowerImage("Bearer ${AppCache.getHelper().token}",img1, img2, img3, img4, img5, img6, img7, img8)
        call.enqueue(object : Callback<ImageResponseData> {
            override fun onResponse(
                call: Call<ImageResponseData>?,
                response: Response<ImageResponseData>?
            ) {
                Log.d("results", response.toString())
                if (response != null) {
                    Log.d("results", response.body().toString())
                }
                if (response != null) {
                    response.body()?.let { onSuccess.invoke(it) }
                } else {
                    onFailure.invoke("Xatolik yuz berdi qaytadan urinib ko'ring")
                }
            }

            override fun onFailure(call: Call<ImageResponseData>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
                Log.d("results", t?.localizedMessage.toString())
            }

        })
    }

    fun getAnnounceByPage(
        page: Int,
        onSuccess: (flowerListPage: GetAnnounceByIndexPage) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getAnnounce("Bearer ${AppCache.getHelper().token}",page)
        call.enqueue(object : Callback<GetAnnounceByIndexPage> {
            override fun onResponse(
                call: Call<GetAnnounceByIndexPage>?,
                response: Response<GetAnnounceByIndexPage>?
            ) {
                if (response != null) {
                    Log.d("listUrl", response.body().toString())
                    response.body()?.let { onSuccess.invoke(it) }
                }
            }

            override fun onFailure(call: Call<GetAnnounceByIndexPage>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
            }

        })
    }

    fun getCustomerPost(
        page: Int,
        onSuccess: (flowerList: ByCategoryID) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).announceOfCustomers("Bearer ${AppCache.getHelper().token}",page)
        call.enqueue(object : Callback<ByCategoryID> {
            override fun onResponse(
                call: Call<ByCategoryID>?,
                response: Response<ByCategoryID>?
            ) {
                if (response != null) {
                    response.body()?.let { onSuccess.invoke(it) }
                }
            }

            override fun onFailure(call: Call<ByCategoryID>?, t: Throwable?) {
                onFailure.invoke("OnOn " + t?.localizedMessage)
            }

        })
    }
    fun getMyAnnounce(
        sellerId: Int,
        page: Int,
        onSuccess: (flowerList: ByCategoryID) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getMyAnnounce("Bearer ${AppCache.getHelper().token}",sellerId,page)
        call.enqueue(object : Callback<ByCategoryID> {
            override fun onResponse(
                call: Call<ByCategoryID>?,
                response: Response<ByCategoryID>?
            ) {
                if (response != null) {
                    response.body()?.let { onSuccess.invoke(it) }
                }
            }

            override fun onFailure(call: Call<ByCategoryID>?, t: Throwable?) {
                onFailure.invoke("OnOn " + t?.localizedMessage)
            }

        })
    }
    fun getYouTubePage(
        page: Int,
        onSuccess: (flowerList: YouTubeLinkPage) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getVideoLinkPage("Bearer ${AppCache.getHelper().token}",page)
        call.enqueue(object : Callback<YouTubeLinkPage> {
            override fun onResponse(
                call: Call<YouTubeLinkPage>?,
                response: Response<YouTubeLinkPage>?
            ) {
                if (response != null) {
                    response.body()?.let { onSuccess.invoke(it) }
                }
            }

            override fun onFailure(call: Call<YouTubeLinkPage>?, t: Throwable?) {
                onFailure.invoke("OnOn " + t?.localizedMessage)
            }

        })
    }

    fun getByDepartmentList(
        departmentId: Int,
        page: Int,
        onSuccess: (list: ByCategoryID) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call =
            apiClient.create(ApiInterface::class.java)
                .getDepartmentId("Bearer ${AppCache.getHelper().token}",departmentId = departmentId, page = page)// TODO: change the places
        call.enqueue(object : Callback<ByCategoryID> {
            override fun onResponse(call: Call<ByCategoryID>?, response: Response<ByCategoryID>?) {
                if (response != null) {
                    response.body()?.let { onSuccess.invoke(it) }
                }
            }

            override fun onFailure(call: Call<ByCategoryID>, t: Throwable?) {
                onFailure.invoke("Ha Ha Ha Again Ha " + t?.localizedMessage)
            }

        })
    }

    fun getByCategoryIDList(
        categoryId: Int,
        page: Int,
        onSuccess: (list: ByCategoryID) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call =
            apiClient.create(ApiInterface::class.java)
                .getOneCategoryPosts("Bearer ${AppCache.getHelper().token}",categoryId = categoryId, page = page)// TODO: change the places
        call.enqueue(object : Callback<ByCategoryID> {
            override fun onResponse(call: Call<ByCategoryID>?, response: Response<ByCategoryID>?) {
                if (response != null) {
                    response.body()?.let { onSuccess.invoke(it) }
                }
            }

            override fun onFailure(call: Call<ByCategoryID>, t: Throwable?) {
                onFailure.invoke("Ha Ha Ha Again Ha " + t?.localizedMessage)
            }

        })
    }

    fun getOneShopList(
        page: Int,
        shopId: Int,
        onSuccess: (list: ByCategoryID) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call =
            apiClient.create(ApiInterface::class.java).getOneShopPosts("Bearer ${AppCache.getHelper().token}",page = page, shopId = shopId)
        call.enqueue(object : Callback<ByCategoryID> {
            override fun onResponse(call: Call<ByCategoryID>?, response: Response<ByCategoryID>?) {
                if (response != null) {
                    response.body()?.let { onSuccess.invoke(it) }
                }
            }

            override fun onFailure(call: Call<ByCategoryID>, t: Throwable?) {
                onFailure.invoke("Ha Ha Ha Again Ha " + t?.localizedMessage)
            }

        })
    }

    fun getOneShopNumber(
        id: Int,
        onSuccess: (list: ShopPhoneNumber) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call =
            apiClient.create(ApiInterface::class.java).getShopPhoneNumber("Bearer ${AppCache.getHelper().token}",shopId = id)
        call.enqueue(object : Callback<ShopPhoneNumber> {
            override fun onResponse(
                call: Call<ShopPhoneNumber>?,
                response: Response<ShopPhoneNumber>?
            ) {
                if (response != null) {
                    response.body()?.let { onSuccess.invoke(it) }
                }
            }

            override fun onFailure(call: Call<ShopPhoneNumber>, t: Throwable?) {
                onFailure.invoke("Ha Ha Ha Again Ha " + t?.localizedMessage)
            }

        })
    }

    fun setAnnounce(
        announceRequestDataResponse: AnnounceRequestData,
        onSuccess: (announceBaseResponse: AnnounceBaseResponse) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        Log.d("url", announceRequestDataResponse.image1.toString())
        Log.d("url", announceRequestDataResponse.image2.toString())
        Log.d("url", announceRequestDataResponse.image3.toString())
        val call =
            apiClient.create(ApiInterface::class.java).setAnnounce("Bearer ${AppCache.getHelper().token}",announceRequestDataResponse)
        call.enqueue(object : Callback<AnnounceBaseResponse> {
            override fun onResponse(
                call: Call<AnnounceBaseResponse>?,
                response: Response<AnnounceBaseResponse>?
            ) {
                if (response != null) {
                    Log.d("results", response.body().toString())
                }
                if (response != null) {
                    response.body()?.let { onSuccess.invoke(it) }
                }
            }

            override fun onFailure(call: Call<AnnounceBaseResponse>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
                Log.d("results", t?.localizedMessage.toString())
            }

        })
    }

    fun getRegion(
        onSuccess: (regionData: RegionData) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getRegion("Bearer ${AppCache.getHelper().token}")
        call.enqueue(object : Callback<RegionData> {
            override fun onResponse(call: Call<RegionData>?, response: Response<RegionData>?) {
                if (response != null) {
                    response.body()?.let { onSuccess.invoke(it) }
                }
            }

            override fun onFailure(call: Call<RegionData>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
            }

        })
    }

    fun getCity(
        id: Int,
        onSuccess: (cityData: ArrayList<CityDataItem>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getCity("Bearer ${AppCache.getHelper().token}",id)
        call.enqueue(object : Callback<CityData> {
            override fun onResponse(call: Call<CityData>?, response: Response<CityData>?) {
                if (response != null) {
                    response.body()?.let { onSuccess.invoke(response.body()!!) }
                }
            }

            override fun onFailure(call: Call<CityData>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
            }

        })
    }

    fun getFlowerType(
        onSuccess: (typeData: FlowerTypeData) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getFlowerType("Bearer ${AppCache.getHelper().token}")
        call.enqueue(object : Callback<FlowerTypeData> {
            override fun onResponse(
                call: Call<FlowerTypeData>?,
                response: Response<FlowerTypeData>?
            ) {
                if (response != null) {
                    response.body()?.let { onSuccess.invoke(it) }
                }
            }

            override fun onFailure(call: Call<FlowerTypeData>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
            }

        })
    }

    fun getShopsList(
        onSuccess: (typeData: List<ShopsListItem>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getShopsList("Bearer ${AppCache.getHelper().token}")
        call.enqueue(object : Callback<List<ShopsListItem>> {
            override fun onResponse(
                call: Call<List<ShopsListItem>>?,
                response: Response<List<ShopsListItem>>?
            ) {
                if (response != null) {
                    response.body()?.let { onSuccess.invoke(it) }
                }
            }

            override fun onFailure(call: Call<List<ShopsListItem>>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
            }

        })
    }

    fun getFlowerTypeById(
        id: Int,
        onSuccess: (typeData: FlowerTypeDataItem) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getFlowerTypeById("Bearer ${AppCache.getHelper().token}",id)
        call.enqueue(object : Callback<BaseResponse<FlowerTypeDataItem>> {
            override fun onResponse(
                call: Call<BaseResponse<FlowerTypeDataItem>>?,
                response: Response<BaseResponse<FlowerTypeDataItem>>?
            ) {
                if (response != null) {
                    response.body()
                        ?.let { response.body()?.`object`?.let { it1 -> onSuccess.invoke(it1) } }
                }
            }

            override fun onFailure(call: Call<BaseResponse<FlowerTypeDataItem>>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
            }

        })
    }

    fun getYouTubeById(
        id: Int,
        onSuccess: (typeData: YouTubeLinkID?) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getVideoLinkById("Bearer ${AppCache.getHelper().token}",id)
        call.enqueue(object : Callback<YouTubeLinkID> {
            override fun onResponse(
                call: Call<YouTubeLinkID>?,
                response: Response<YouTubeLinkID>?
            ) {
                if (response != null) {
                    response.body()?.let {
                        onSuccess.invoke(it)
                    }
                    Log.d("YUTAG", response.body().toString())
                }
            }

            override fun onFailure(call: Call<YouTubeLinkID>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
            }

        })
    }

    fun getReklamaById(
        id: Int,
        onSuccess: (typeData: ReklamaImages?) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getReklamaId("Bearer ${AppCache.getHelper().token}",id)
        call.enqueue(object : Callback<ReklamaImages> {
            override fun onResponse(
                call: Call<ReklamaImages>?,
                response: Response<ReklamaImages>?
            ) {
                if (response != null) {
                    response.body()?.let {
                        onSuccess.invoke(it)
                    }
                    Log.d("YUTAG", response.body().toString())
                }
            }

            override fun onFailure(call: Call<ReklamaImages>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
            }

        })
    }

    fun getByParentCatID(
        id: Int,
        onSuccess: (typeData: List<ByParentIDItem>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getCategoryParentByID("Bearer ${AppCache.getHelper().token}",id)
        call.enqueue(object : Callback<List<ByParentIDItem>> {
            override fun onResponse(
                call: Call<List<ByParentIDItem>>?,
                response: Response<List<ByParentIDItem>>?
            ) {
                if (response != null) {
                    response.body()?.let {
                        onSuccess.invoke(it)
                    }
                    Log.d("YUTAG", response.body().toString())
                }
            }

            override fun onFailure(call: Call<List<ByParentIDItem>>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
            }

        })
    }

    fun createShop(
        createShopRequest: CreateShopRequest,
        onSuccess: (createShopRequest: CreateShopRequest) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).createShop("Bearer ${AppCache.getHelper().token}",createShopRequest)
        call.enqueue(object : Callback<BaseResponse<CreateShopRequest>> {
            override fun onResponse(
                call: Call<BaseResponse<CreateShopRequest>>?,
                response: Response<BaseResponse<CreateShopRequest>>?
            ) {
                if (response != null) {
                    Log.d("resultsShop", response.body()?.`object`.toString())
                }
                if (response != null) {
                    response.body()?.`object`?.let { onSuccess.invoke(it) }
                }
            }

            override fun onFailure(call: Call<BaseResponse<CreateShopRequest>>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
                Log.d("results", t?.localizedMessage.toString())
            }

        })
    }

    fun deleteAnnounceById(
        announceId: Int,
        onSuccess: (data: Any) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).deleteAnnounceById("Bearer ${AppCache.getHelper().token}",announceId)
        call.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>?, response: Response<Any>?) {
                if (response != null) {
                    Log.d("resultsShop", response.body().toString())
                }
                if (response != null) {
                    response.body()?.let { onSuccess.invoke(it) }
                }
            }

            override fun onFailure(call: Call<Any>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
                Log.d("results", t?.localizedMessage.toString())
            }

        })
    }

    fun updateShopId(
        sellerId: Int,
        userEditRequest: UserEditRequest,
        onSuccess: (data: Any) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).updateShopId("Bearer ${AppCache.getHelper().token}",sellerId, userEditRequest)
        call.enqueue(object : Callback<Any> {
            override fun onResponse(
                call: Call<Any>?,
                response: Response<Any>?
            ) {
                if (response?.body() != null) {
                    response.body()?.let { onSuccess.invoke(it) }
                }else{
                    val errorResponse = Gson().fromJson(response?.errorBody()!!.charStream(), ErrorResponse::class.java)
                  onFailure.invoke(errorResponse.massage)
                }
            }

            override fun onFailure(call: Call<Any>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
                Log.d("results", t?.localizedMessage.toString())
            }

        })
    }

    fun smsSend(
        token: String,
        data: RequestBody,
        onSuccess: (status: String) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val client = OkHttpClient().newBuilder()
            .build()
        val request: Request = Request.Builder()
            .url("https://notify.eskiz.uz/api/message/sms/send")
            .method("POST", data)
            .addHeader("Authorization", "Bearer $token")
            .build()
        val call = client.newCall(request)
        call.enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                onFailure.invoke(e.localizedMessage)
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                CoroutineScope(Dispatchers.Main).launch {
                    if (response.isSuccessful) {
                        onSuccess.invoke("success")
                    }else{
                        onFailure.invoke("Qandaydir xatolik yuz berdi qayta urinib ko'ring")
                    }
                }
            }
        })
    }

    fun getUserData(
        userId: Int,
        onSuccess: (userData: UserDataResponse) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getUserData("Bearer ${AppCache.getHelper().token}",userId)
        call.enqueue(object : Callback<BaseResponse<UserDataResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<UserDataResponse>>?,
                response: Response<BaseResponse<UserDataResponse>>?
            ) {
                if (response != null) {
                    response.body()?.`object`.let {
                        if (it != null) {
                            onSuccess.invoke(it)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse<UserDataResponse>>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
            }

        })
    }
    fun getSmsToken(
        key: Long,
        id: Int,
        onSuccess: (smsData: SmsTokenResponse) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getSMSToken(key,id)
        call.enqueue(object : Callback<SmsTokenResponse> {
            override fun onResponse(
                call: Call<SmsTokenResponse>?,
                response: Response<SmsTokenResponse>?
            ) {
                response?.body()?.let {
                    onSuccess.invoke(it)
                }
            }

            override fun onFailure(call: Call<SmsTokenResponse>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
            }

        })
    }
    fun checkPhoneNumber(
        loginRequest: LoginRequest,
        onSuccess: (smsData: LoginResponse) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).checkPhoneNumber(loginRequest)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>?,
                response: Response<LoginResponse>?
            ) {
                if (response != null) {
                    if (response.code() == 200) {
                        response.body()?.let {
                            onSuccess.invoke(it)
                        }
                    } else if (response.code()==409) {
                        val errorResponse = Gson().fromJson(response.errorBody()!!.charStream(), ErrorResponse::class.java)
                        if (errorResponse.massage=="Bu telefon raqam ro'yxatdan o'tmagan"){
                            onFailure.invoke("unregistered")
                        }else{
                            onFailure.invoke(errorResponse.massage)
                        }
                    } else if (response.code() in 400..499) {
                        onFailure.invoke("networkError")
                    }
                    else {
                        onFailure.invoke("Error")
                    }
                }

            }
            override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)

            }

        })
    }
    fun getShopId(
        onSuccess: (shopId: Int) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getShopsList("Bearer ${AppCache.getHelper().token}")
        call.enqueue(object : Callback<List<ShopsListItem>> {
            override fun onResponse(
                call: Call<List<ShopsListItem>>?,
                response: Response<List<ShopsListItem>>?
            ) {
                if (response != null) {
                    response.body()?.forEach {
                        if (it.sellerId==AppCache.getHelper().userId){
                            onSuccess.invoke(it.id)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<ShopsListItem>>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
            }

        })
    }
}