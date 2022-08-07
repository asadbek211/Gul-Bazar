package com.bizmiz.gulbozor.ui.start.authentication.login.core

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("phoneNumber")
    val phoneNumber: String
)