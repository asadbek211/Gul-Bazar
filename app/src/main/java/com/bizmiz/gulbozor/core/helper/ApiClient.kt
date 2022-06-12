package com.bizmiz.gulbozor.core.helper

import com.bizmiz.gulbozor.core.utils.Constant
import com.bizmiz.gulbozor.ui.start.authentication.login.core.LoginService
import com.bizmiz.gulbozor.ui.start.authentication.signUp.core.RegistrationService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
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
            if (!Companion::retrofit.isInitialized){
                val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BODY
                }
                val client: OkHttpClient = OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build()
                retrofit = Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit
        }

        fun getRegisterService(): RegistrationService {
            return getClient().create(RegistrationService::class.java)
        }

        fun getLoginService(): LoginService {
            return getClient().create(LoginService::class.java)
        }
    }
}