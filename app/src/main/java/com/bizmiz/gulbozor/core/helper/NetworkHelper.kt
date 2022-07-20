package com.bizmiz.gulbozor.core.helper

import android.util.Log
import com.bizmiz.gulbozor.core.models.*
import com.bizmiz.gulbozor.core.models.youtube.getVideoLinkById.YouTubeLinkID
import com.bizmiz.gulbozor.core.models.youtube.getVideoLinkPage.YouTubeLinkPage
import com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category.ShopsListItem
import com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category.oneShop.model.OneShopData
import com.bizmiz.gulbozor.ui.model.ImageResponseData
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

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
            .addFlowerImage(img1, img2, img3, img4, img5, img6, img7, img8)
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
        announceDataResponse: AnnounceData,
        onSuccess: (announceResponse: AnnounceResponse) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        Log.d("url", announceDataResponse.image1.toString())
        Log.d("url", announceDataResponse.image2.toString())
        Log.d("url", announceDataResponse.image3.toString())
        val call = apiClient.create(ApiInterface::class.java).setAnnounce(announceDataResponse)
        call.enqueue(object : Callback<AnnounceResponse> {
            override fun onResponse(
                call: Call<AnnounceResponse>?,
                response: Response<AnnounceResponse>?
            ) {
                if (response != null) {
                    Log.d("results", response.body().toString())
                }
                if (response != null) {
                    response.body()?.let { onSuccess.invoke(it) }
                }
            }

            override fun onFailure(call: Call<AnnounceResponse>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
                Log.d("results", t?.localizedMessage.toString())
            }

        })
    }

    fun getRegion(
        onSuccess: (regionData: List<String>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getRegion()
        val list: ArrayList<String> = arrayListOf()
        call.enqueue(object : Callback<RegionData> {
            override fun onResponse(call: Call<RegionData>?, response: Response<RegionData>?) {
                if (response != null) {
                    response.body()?.forEach {
                        list.add(it.name)
                    }
                    response.body()?.let { onSuccess.invoke(list) }
                }
            }

            override fun onFailure(call: Call<RegionData>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
            }

        })
    }

    fun getCity(
        id: Int,
        onSuccess: (cityData: List<String>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getCity()
        val list: ArrayList<String> = arrayListOf()
        call.enqueue(object : Callback<CityData> {
            override fun onResponse(call: Call<CityData>?, response: Response<CityData>?) {
                if (response != null) {
                    response.body()?.forEach {
                        if (it.regionId == id) {
                            list.add(it.name)
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

    fun getByParentCatID(
        id: Int,
        onSuccess: (typeData: List<AnnounceData>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getCategoryParentByID(id)
        call.enqueue(object : Callback<List<AnnounceData>> {
            override fun onResponse(
                call: Call<List<AnnounceData>>?,
                response: Response<List<AnnounceData>>?
            ) {
                if (response != null) {
                    response.body()?.let {
                        onSuccess.invoke(it)
                    }
                    Log.d("YUTAG", response.body().toString())
                }
            }

            override fun onFailure(call: Call<List<AnnounceData>>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
            }

        })
    }

    fun getAnnounce(
        onSuccess: (flowerList: List<AnnounceData>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getAnnounce()
        call.enqueue(object : Callback<List<AnnounceData>> {
            override fun onResponse(
                call: Call<List<AnnounceData>>?,
                response: Response<List<AnnounceData>>?
            ) {
                if (response != null) {
                    Log.d("listUrl", response.body().toString())
                    response.body()?.let { onSuccess.invoke(it) }
                }
            }

            override fun onFailure(call: Call<List<AnnounceData>>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
            }

        })
    }


}