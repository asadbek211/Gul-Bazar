package com.bizmiz.gulbozor.ui.start.authentication.login.MVP

import com.bizmiz.gulbozor.core.caches.AppCache
import com.bizmiz.gulbozor.core.helper.ApiClient
import com.bizmiz.gulbozor.ui.start.authentication.login.core.LoginRequest
import com.bizmiz.gulbozor.ui.start.authentication.login.core.LoginResponse
import com.bizmiz.gulbozor.ui.start.authentication.login.core.LoginService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response

class LoginPresenter(val view: LoginMVP.View) : LoginMVP.Presenter {

    var loginService: LoginService

    init {
        loginService = ApiClient.getLoginService()
    }

    private val compositeDisposable = CompositeDisposable()

    override fun loginWithPhoneNumber(phoneNumber: String) {
        val body = LoginRequest(
            phoneNumber = phoneNumber
        )
        val disposable = loginService.loginGetToken(loginRequest = body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Response<LoginResponse>>() {
                override fun onSuccess(t: Response<LoginResponse>) {
                    if (t.code() == 200) {
                        view.setData("successful")
                        AppCache.getHelper().token = t.body()?.token
                        AppCache.getHelper().userId = t.body()?.user_id!!
                    } else if (t.code() in 400..499) {
                        view.setData("networkError")
                    } else {
                        view.setData("Error")
                    }
                }

                override fun onError(e: Throwable) {
                    view.onError(e.message.toString())
                }

            })
        compositeDisposable.addAll(disposable)

    }

    override fun cancelRequest() {
        compositeDisposable.dispose()
    }
}