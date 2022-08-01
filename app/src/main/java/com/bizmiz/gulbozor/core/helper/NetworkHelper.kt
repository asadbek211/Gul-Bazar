package com.bizmiz.gulbozor.core.helper

import android.util.Log
import com.bizmiz.gulbozor.core.models.*
import com.bizmiz.gulbozor.core.models.category.ByParentIDItem
import com.bizmiz.gulbozor.core.models.shop.CreateShopRequest
import com.bizmiz.gulbozor.core.models.slideReklama.ReklamaImages
import com.bizmiz.gulbozor.core.models.user.UserDataResponse
import com.bizmiz.gulbozor.core.models.youtube.getVideoLinkById.YouTubeLinkID
import com.bizmiz.gulbozor.core.models.youtube.getVideoLinkPage.YouTubeLinkPage
import com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category.ShopsListItem
import com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category.oneShop.model.OneShopData
import com.bizmiz.gulbozor.ui.model.ImageResponseData
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
        val call = apiClient.create(ApiInterface::class.java).addFlowerImage(img1,img2,img3,img4, img5, img6, img7, img8)
        call.enqueue(object : Callback<ImageResponseData> {
            override fun onResponse(call: Call<ImageResponseData>?, response: Response<ImageResponseData>?) {
                Log.d("results", response.toString())
                if (response != null) {
                    Log.d("results", response.body().toString())
                }
                if (response != null) {
                    response.body()?.let { onSuccess.invoke(it) }
                }else{
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
        onSuccess: (flowerListPage: AnnounceResponseData) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getAnnounce(page)
        call.enqueue(object : Callback<AnnounceResponseData> {
            override fun onResponse(
                call: Call<AnnounceResponseData>?,
                response: Response<AnnounceResponseData>?
            ) {
                if (response != null) {
                    Log.d("listUrl", response.body().toString())
                    response.body()?.let { onSuccess.invoke(it) }
                }
            }

            override fun onFailure(call: Call<AnnounceResponseData>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
            }

        })
    }

    fun getYouTubePage(
        page: Int,
        onSuccess: (flowerList: YouTubeLinkPage) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getVideoLinkPage(page)
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

    fun getOneShopList(
        page: Int,
        shopId: Int,
        onSuccess: (list: OneShopData) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call =
            apiClient.create(ApiInterface::class.java).getOneShopPosts(page = page, shopId = shopId)
        call.enqueue(object : Callback<OneShopData> {
            override fun onResponse(call: Call<OneShopData>?, response: Response<OneShopData>?) {
                if (response != null) {
                    response.body()?.let { onSuccess.invoke(it) }
                }
            }

            override fun onFailure(call: Call<OneShopData>, t: Throwable?) {
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
        val call = apiClient.create(ApiInterface::class.java).setAnnounce(announceRequestDataResponse)
        call.enqueue(object : Callback<AnnounceBaseResponse> {
            override fun onResponse(call: Call<AnnounceBaseResponse>?, response: Response<AnnounceBaseResponse>?) {
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
        val call = apiClient.create(ApiInterface::class.java).getRegion()
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
        id:Int,
        onSuccess: (cityData: ArrayList<CityDataItem>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getCity()
        val list:ArrayList<CityDataItem> = arrayListOf()
        call.enqueue(object : Callback<CityData> {
            override fun onResponse(call: Call<CityData>?, response: Response<CityData>?) {
                if (response != null) {
                    response.body()?.forEach {
                        if (it.regionId==id){
                            list.add(it)
                        }
                    }
                    response.body()?.let { onSuccess.invoke(list) }
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
        val call = apiClient.create(ApiInterface::class.java).getFlowerType()
        call.enqueue(object : Callback<FlowerTypeData> {
            override fun onResponse(call: Call<FlowerTypeData>?, response: Response<FlowerTypeData>?) {
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
        val call = apiClient.create(ApiInterface::class.java).getShopsList()
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
        id:Int,
        onSuccess: (typeData: FlowerTypeDataItem) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getFlowerTypeById(id)
        call.enqueue(object : Callback<BaseResponse<FlowerTypeDataItem>> {
            override fun onResponse(call: Call<BaseResponse<FlowerTypeDataItem>>?, response: Response<BaseResponse<FlowerTypeDataItem>>?) {
                if (response != null) {
                    response.body()?.let { response.body()?.`object`?.let { it1 -> onSuccess.invoke(it1) } }
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
        val call = apiClient.create(ApiInterface::class.java).getVideoLinkById(id)
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
        val call = apiClient.create(ApiInterface::class.java).getReklamaId(id)
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
        val call = apiClient.create(ApiInterface::class.java).getCategoryParentByID(id)
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
        val call = apiClient.create(ApiInterface::class.java).createShop(createShopRequest)
        call.enqueue(object : Callback<BaseResponse<CreateShopRequest>> {
            override fun onResponse(call: Call<BaseResponse<CreateShopRequest>>?, response: Response<BaseResponse<CreateShopRequest>>?) {
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
        announceId:Int,
        onSuccess: (data:Any) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).deleteAnnounceById(announceId)
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
        shopId: Int,
        onSuccess: (data: Any) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).updateShopId(sellerId,shopId)
        call.enqueue(object : Callback<BaseResponse<Any>> {
            override fun onResponse(call: Call<BaseResponse<Any>>?, response: Response<BaseResponse<Any>>?) {
                if (response != null) {
                    Log.d("resultsShopId", response.toString())
                    Log.d("resultsShopId", response.body()?.`object`.toString())
                }
                if (response != null) {
                    response.body()?.`object`?.let { onSuccess.invoke(it) }
                }
            }
            override fun onFailure(call: Call<BaseResponse<Any>>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
                Log.d("results", t?.localizedMessage.toString())
            }

        })
    }
    fun smsSend(
        token:String,
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
                    if (response.isSuccessful){
                        onSuccess.invoke("success")
                    }
                }
            }
        })
    }
    fun getUserData(
        userId:Int,
        onSuccess: (userData:UserDataResponse) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getUserData(userId)
        call.enqueue(object : Callback<BaseResponse<UserDataResponse>> {
            override fun onResponse(call: Call<BaseResponse<UserDataResponse>>?, response: Response<BaseResponse<UserDataResponse>>?) {
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
}