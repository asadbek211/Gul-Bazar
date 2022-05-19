package com.bizmiz.gulbozor.di

import com.bizmiz.gulbozor.helper.ApiClient
import com.bizmiz.gulbozor.helper.NetworkHelper
import com.bizmiz.gulbozor.ui.add.AddFlowerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single { ApiClient.getClient() }
    single { NetworkHelper(get()) }
}
val viewModelModule = module {
    viewModel { AddFlowerViewModel(get()) }
}