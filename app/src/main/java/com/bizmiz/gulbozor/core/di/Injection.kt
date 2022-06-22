package com.bizmiz.gulbozor.core.di

import com.bizmiz.gulbozor.core.helper.ApiClient
import com.bizmiz.gulbozor.core.helper.NetworkHelper
import com.bizmiz.gulbozor.ui.bottom_nav.add.AddFlowerViewModel
import com.bizmiz.gulbozor.ui.bottom_nav.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single { ApiClient.getClient() }
    single { NetworkHelper(get()) }
}
val viewModelModule = module {
    viewModel { AddFlowerViewModel(get()) }
    viewModel { HomeViewModel(get()) }
}