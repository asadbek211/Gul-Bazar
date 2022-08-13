package com.bizmiz.gulbozor.ui.start.authentication.signUp.MVP

import android.util.Log
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

    override fun sendRegisterData(phoneNumber: String, shopId: Int, userSurname: String,userName: String) {
        val body = RegistrationRequest(
            phoneNumber,
            shopId,
            userSurname,
            userName
        )
        val disposable = registerService.registerSend(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Response<Any>>() {
                override fun onSuccess(t: Response<Any>) {
                    Log.d("responseAny",t.body().toString())
                    if (t.code() == 200) {
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