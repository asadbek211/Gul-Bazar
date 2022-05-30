package com.bizmiz.gulbozor.helper

import android.util.Log
import com.bizmiz.gulbozor.ui.model.AnnounceResponse
import com.bizmiz.gulbozor.ui.model.FlowerListResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class NetworkHelper(
    private val apiClient: Retrofit
) {
    fun addFlowerImage(
        partBody: MultipartBody.Part,
        onSuccess: (flowerId: Int) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).addFlowerImage(partBody)
        call.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>?, response: Response<Any>?) {
                Log.d("results", response.toString())
                if (response != null) {
                    Log.d("results", response.body().toString())
                }
                if (response != null) {
//                    response.body()?.let { onSuccess.invoke(it) }
                }
            }

            override fun onFailure(call: Call<Any>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
                Log.d("results", t?.localizedMessage.toString())
            }

        })
    }
    fun getAnnounce(
        onSuccess: (flowerList: List<FlowerListResponse>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getAnnounce()
        call.enqueue(object : Callback<List<FlowerListResponse>> {
            override fun onResponse(call: Call<List<FlowerListResponse>>?, response: Response<List<FlowerListResponse>>?) {
                if (response != null) {
                    response.body()?.let { onSuccess.invoke(it) }
                }
            }

            override fun onFailure(call: Call<List<FlowerListResponse>>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
            }

        })
    }
    fun setAnnounce(
        flowerListResponse: FlowerListResponse,
        onSuccess: (announceResponse: AnnounceResponse) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).setAnnounce(flowerListResponse)
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