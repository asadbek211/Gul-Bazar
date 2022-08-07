package com.bizmiz.gulbozor.ui.start.authentication.signUp.MVP

interface RegistrationMVP {
    interface View {
        fun isRegister(isLoading: Boolean)
        fun onError(message: String)
    }

    interface Presenter {
        fun sendRegisterData(
            userName: String,
            userSurname: String,
            phoneNumber: String
        )

        fun cancelRequest()
    }
}