package com.bizmiz.gulbozor.ui.start.authentication.login.core

import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("/auth/login")
    fun loginGetToken(@Body loginRequest: LoginRequest): Single<Response<LoginResponse>>
}