package com.bizmiz.gulbozor.ui.start.authentication.login.core

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("id")
    val id: String
)