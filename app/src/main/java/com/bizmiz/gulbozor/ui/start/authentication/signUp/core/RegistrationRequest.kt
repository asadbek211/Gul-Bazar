package com.bizmiz.gulbozor.ui.start.authentication.signUp.core

import com.google.gson.annotations.SerializedName

data class RegistrationRequest(
    @SerializedName("password")
    val password: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("userName")
    val userName: String,
)