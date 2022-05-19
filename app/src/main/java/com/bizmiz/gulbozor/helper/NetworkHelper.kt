package com.bizmiz.gulbozor.helper

import android.util.Log
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
        call.enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>?, response: Response<Int>?) {
                Log.d("results", response.toString())
                if (response != null) {
                    Log.d("results", response.body().toString())
                }
                if (response != null) {
                    response.body()?.let { onSuccess.invoke(it) }
                }
            }

            override fun onFailure(call: Call<Int>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
                Log.d("results", t?.localizedMessage.toString())
            }

        })
    }
}