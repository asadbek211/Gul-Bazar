package com.bizmiz.gulbozor.core.models.user.edit_user

data class UserEditRequest(
    val password: String,
    val phoneNumber: String,
    val shopId: Int,
    val surname: String,
    val userName: String
)