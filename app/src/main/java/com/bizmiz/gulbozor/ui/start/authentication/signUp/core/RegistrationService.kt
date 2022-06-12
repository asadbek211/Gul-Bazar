package com.bizmiz.gulbozor.ui.start.authentication.signUp.core

import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationService {
    @POST("/auth/registerUser")
    fun registerSend(@Body loginRequest: RegistrationRequest): Single<Response<Any>>
}