package com.sun.adsfinder01.di

import com.sun.adsfinder01.ui.login.LoginViewModel
import com.sun.adsfinder01.ui.register.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }

    viewModel { RegistrationViewModel(get()) }
}
