package com.bizmiz.gulbozor.ui.start.authentication.signUp.core

import com.google.gson.annotations.SerializedName

data class RegistrationRequest(
    @SerializedName("surname")
    val surname: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("name")
    val name: String,
)