package com.bizmiz.gulbozor.core.di

import com.bizmiz.gulbozor.core.helper.ApiClient
import com.bizmiz.gulbozor.core.helper.NetworkHelper
import com.bizmiz.gulbozor.ui.bottom_nav.add.categorys.add_buket.AddBuketViewModel
import com.bizmiz.gulbozor.ui.bottom_nav.add.categorys.add_fertilizers.AddFertilizersViewModel
import com.bizmiz.gulbozor.ui.bottom_nav.add.categorys.add_houseplants.AddFlowerViewModel
import com.bizmiz.gulbozor.ui.bottom_nav.add.categorys.add_pots.AddPotViewModel
import com.bizmiz.gulbozor.ui.bottom_nav.add.categorys.add_tree.AddTreeViewModel
import com.bizmiz.gulbozor.ui.bottom_nav.categories.oneCategory.OneTypeOfCatVM
import com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category.ShopsViewModel
import com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category.oneShop.OnShopVM
import com.bizmiz.gulbozor.ui.bottom_nav.home.HomeViewModel
import com.bizmiz.gulbozor.ui.bottom_nav.home.details.buket_details.BuketDetailsViewModel
import com.bizmiz.gulbozor.ui.bottom_nav.home.details.edit_announce.EditAnnounceViewModel
import com.bizmiz.gulbozor.ui.bottom_nav.home.details.fetilizers_details.FetilizersDetailsViewModel
import com.bizmiz.gulbozor.ui.bottom_nav.home.details.houseplants_details.FlowerDetailsViewModel
import com.bizmiz.gulbozor.ui.bottom_nav.home.details.pot_details.PotDetailsViewModel
import com.bizmiz.gulbozor.ui.bottom_nav.home.details.tree_details.TreeDetailsViewModel
import com.bizmiz.gulbozor.ui.bottom_nav.profile.ProfileViewModel
import com.bizmiz.gulbozor.ui.bottom_nav.profile.shop.CreateShopViewModel
import com.bizmiz.gulbozor.ui.start.authentication.sms_verify.SmsVerifyViewModel
import com.bizmiz.gulbozor.ui.youtube.YouTubeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single { ApiClient.getClient() }
    single { NetworkHelper(get()) }
}
val viewModelModule = module {
    viewModel { OnShopVM(get()) }
    viewModel { ShopsViewModel(get()) }
    viewModel { OneTypeOfCatVM(get()) }
    viewModel { YouTubeViewModel(get()) }
    viewModel { AddFlowerViewModel(get()) }
    viewModel { AddBuketViewModel(get()) }
    viewModel { AddTreeViewModel(get()) }
    viewModel { AddPotViewModel(get()) }
    viewModel { AddFertilizersViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { FlowerDetailsViewModel(get()) }
    viewModel { TreeDetailsViewModel(get()) }
    viewModel { PotDetailsViewModel(get()) }
    viewModel { FetilizersDetailsViewModel(get()) }
    viewModel { BuketDetailsViewModel(get()) }
    viewModel { EditAnnounceViewModel(get()) }
    viewModel { CreateShopViewModel(get()) }
    viewModel { SmsVerifyViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
}