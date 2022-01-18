package com.heartsun.chainpurkhanepani.di


import com.heartsun.chainpurkhanepani.ui.HomeViewModel
import com.heartsun.chainpurkhanepani.ui.memberRegisterRequest.RegisterViewModel
import com.heartsun.chainpurkhanepani.ui.meroKhaniPani.MyTapViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(),get()) }
    viewModel { RegisterViewModel(get(),get()) }
    viewModel { MyTapViewModel(get(),get()) }

}


