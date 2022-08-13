package com.bizmiz.gulbozor.core.models


data class LoginResponse(
    val success: Boolean,
    val massage: String?,
    val token: String?,
    val user_id: Long?

)