package com.bizmiz.gulbozor.ui.start.authentication.login.MVP

import android.util.Log
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

    override fun loginWithPhoneNumber(phoneNumber: String, password: String) {
        val body = LoginRequest(
            phoneNumber = phoneNumber,
            password = password
        )
        Log.d("tel",phoneNumber)
        val disposable = loginService.loginGetToken(loginRequest = body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Response<LoginResponse>>() {
                override fun onSuccess(t: Response<LoginResponse>) {

                    Log.d("TAGAAA", t.code().toString())

                    if (t.code() == 200) {
                        view.setData("successful")
                        AppCache.getHelper().token = t.body()?.token
                        AppCache.getHelper().userId = t.body()?.user_id!!
                        Log.d("TAGVVV", t.body()?.user_id.toString())
                    } else if (t.code() in 400..499) {
                        view.setData("networkError")
                    } else {
                        view.setData("Error")
                    }
                }

                override fun onError(e: Throwable) {
                    view.onError(e.message.toString())

                    Log.d("TAGAAAQ", e.message.toString())
                }

            })
        compositeDisposable.addAll(disposable)

    }

    override fun cancelRequest() {
        compositeDisposable.dispose()
    }
}