package com.bizmiz.gulbozor.ui.start.authentication.signUp.core

import com.google.gson.annotations.SerializedName

data class RegistrationRequest(
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("shopId")
    val shopId: Int,
    @SerializedName("surname")
    val surname: String,
    @SerializedName("userName")
    val userName: String
)