package com.bizmiz.gulbozor.helper

import android.util.Log
import com.bizmiz.gulbozor.ui.model.AnnounceDataResponse
import com.bizmiz.gulbozor.ui.model.AnnounceResponse
import com.bizmiz.gulbozor.ui.model.FlowerListResponse
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
                }
            }

            override fun onFailure(call: Call<ImageResponseData>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
                Log.d("results", t?.localizedMessage.toString())
            }

        })
    }
    fun getAnnounce(
        onSuccess: (flowerList: List<AnnounceDataResponse>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getAnnounce()
        call.enqueue(object : Callback<List<AnnounceDataResponse>> {
            override fun onResponse(call: Call<List<AnnounceDataResponse>>?, response: Response<List<AnnounceDataResponse>>?) {
                if (response != null) {
                    Log.d("listUrl", response.body().toString())
                    response.body()?.let { onSuccess.invoke(it) }
                }
            }

            override fun onFailure(call: Call<List<AnnounceDataResponse>>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
            }

        })
    }
    fun setAnnounce(
        announceDataResponse: AnnounceDataResponse,
        onSuccess: (announceResponse: AnnounceResponse) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        Log.d("url", announceDataResponse.image1.toString())
        Log.d("url", announceDataResponse.image2.toString())
        Log.d("url", announceDataResponse.image3.toString())
        val call = apiClient.create(ApiInterface::class.java).setAnnounce(announceDataResponse)
        call.enqueue(object : Callback<AnnounceResponse> {
            override fun onResponse(call: Call<AnnounceResponse>?, response: Response<AnnounceResponse>?) {
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
}