package com.bizmiz.gulbozor.ui.start.authentication.login.core

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("token")
    val token: String,
    @SerializedName("user_id")
    val user_id: Int,
    @SerializedName("message")
    val message: String? = null
)