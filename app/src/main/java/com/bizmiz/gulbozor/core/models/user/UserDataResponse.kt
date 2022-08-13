package com.bizmiz.gulbozor.core.models.user

data class UserDataResponse(
    val accountNonExpired: Boolean,
    val accountNonLocked: Boolean,
    val authorities: List<Authority>,
    val createAt: Long,
    val credentialsNonExpired: Boolean,
    val enabled: Boolean,
    val id: Int,
    val password: String,
    val phoneNumber: String,
    val phoneNumberTest: String,
    val roles: List<Role>,
    val shopId: Int,
    val updateAt: String,
    val surname: String,
    val username: String
)