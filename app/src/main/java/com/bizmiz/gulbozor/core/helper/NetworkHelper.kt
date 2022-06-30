package com.bizmiz.gulbozor.core.helper

import android.util.Log
import com.bizmiz.gulbozor.core.models.*
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
    fun getAnnounce(
        onSuccess: (flowerList: List<AnnounceResponseData>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getAnnounce()
        call.enqueue(object : Callback<List<AnnounceResponseData>> {
            override fun onResponse(call: Call<List<AnnounceResponseData>>?, response: Response<List<AnnounceResponseData>>?) {
                if (response != null) {
                    Log.d("listUrl", response.body().toString())
                    response.body()?.let { onSuccess.invoke(it) }
                }
            }

            override fun onFailure(call: Call<List<AnnounceResponseData>>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
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
}