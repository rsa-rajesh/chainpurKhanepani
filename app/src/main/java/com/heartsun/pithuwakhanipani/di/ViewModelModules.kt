package com.heartsun.pithuwakhanipani.di


import com.heartsun.pithuwakhanipani.ui.HomeViewModel
import com.heartsun.pithuwakhanipani.ui.memberRegisterRequest.RegisterViewModel
import com.heartsun.pithuwakhanipani.ui.meroKhaniPani.MyTapViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(),get()) }
    viewModel { RegisterViewModel(get(),get()) }
    viewModel { MyTapViewModel(get(),get()) }

}


