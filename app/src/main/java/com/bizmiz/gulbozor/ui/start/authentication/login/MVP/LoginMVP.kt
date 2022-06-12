package com.bizmiz.gulbozor.ui.start.authentication.login.MVP

interface LoginMVP {

    interface Presenter {
        fun loginWithPhoneNumber(
            phoneNumber: String,
            password: String,
        )

        fun cancelRequest()
    }

    interface View {
        fun isLoading(isLoading: Boolean)
        fun onError(message: String)
        fun setData(message: String)
    }
}