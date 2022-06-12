package com.bizmiz.gulbozor.ui.start.authentication.signUp.MVP

import com.bizmiz.gulbozor.core.helper.ApiClient
import com.bizmiz.gulbozor.ui.start.authentication.signUp.core.RegistrationRequest
import com.bizmiz.gulbozor.ui.start.authentication.signUp.core.RegistrationService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response

class RegistrationPresenter(val view: RegistrationMVP.View) : RegistrationMVP.Presenter {

    var registerService: RegistrationService

    init {
        registerService = ApiClient.getRegisterService()
    }

    private val compositeDisposable = CompositeDisposable()

    override fun sendRegisterData(userName: String, phoneNumber: String, password: String) {
        val body = RegistrationRequest(
            userName = userName,
            phoneNumber = phoneNumber,
            password = password
        )
        val disposable = registerService.registerSend(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Response<Any>>() {
                override fun onSuccess(t: Response<Any>) {

                    if (t.code() == 201) {
                        view.isRegister(true)
                    } else {
                        view.isRegister(false)
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