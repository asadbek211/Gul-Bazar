package com.bizmiz.gulbozor.ui.start.authentication.signUp.MVP

interface RegistrationMVP {
    interface View {
        fun isRegister(isLoading: Boolean)
        fun onError(message: String)
    }

    interface Presenter {
        fun sendRegisterData(
            phoneNumber: String,
            shopId: Int,
            userSurname: String,
            userName: String
        )
        fun cancelRequest()
    }
}