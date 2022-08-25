package com.bizmiz.gulbozor.core.helper

import android.content.Context
import com.bizmiz.gulbozor.core.app.App
import com.bizmiz.gulbozor.core.caches.AppCache
import com.bizmiz.gulbozor.core.utils.Constant
import com.bizmiz.gulbozor.ui.start.authentication.signUp.core.RegistrationService
/*import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager*/
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object {
        private lateinit var retrofit: Retrofit
        private lateinit var gson: Gson
        fun getClient(): Retrofit {
            if (!Companion::gson.isInitialized) {
                gson = GsonBuilder()
                    .setLenient()
                    .create()
            }
            if (!Companion::retrofit.isInitialized) {
                val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BODY
                }
                val client: OkHttpClient = OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    //.addInterceptor(getChuckerInterception(getChuckerCollector()))
                    .addInterceptor(interceptor)
                    .addInterceptor(interceptor(App.instance))
                    .build()
                retrofit = Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .client(client)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit
        }

        fun getRegisterService(): RegistrationService {
            return getClient().create(RegistrationService::class.java)
        }
        /* private fun getChuckerCollector(): ChuckerCollector {

             return ChuckerCollector(
                 context = App.instance!!,
                 showNotification = true,
                 retentionPeriod = RetentionManager.Period.FOREVER
             )

         }*/

        private fun interceptor(
            context: Context?,
        ): Interceptor {
            return Interceptor { chain: Interceptor.Chain ->
                val request = chain.request()
                val builder: Request.Builder = request.newBuilder()
                builder
                    .addHeader("Authorization", "Bearer ${AppCache.getHelper().token}")
                val response = chain.proceed(builder.build())
                response
            }
        }

        /*private fun getChuckerInterception(chuckerCollector: ChuckerCollector): ChuckerInterceptor {

            return ChuckerInterceptor.Builder(App.instance!!)
                .collector(getChuckerCollector())
                .maxContentLength(250_000L)
                .alwaysReadResponseBody(true)
                .build()
        }*/

    }

}