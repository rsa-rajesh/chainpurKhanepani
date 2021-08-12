package com.heartsun.pithuwakhanipani.di


import com.heartsun.pithuwakhanipani.ui.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
//    viewModel { AuthViewModel(get(), get()) }
//    viewModel { ProfileViewModel(get()) }
    viewModel { HomeViewModel(get(),get()) }
}


