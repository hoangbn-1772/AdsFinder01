package com.sun.adsfinder01.di

import com.sun.adsfinder01.ui.home.HomeViewModel
import com.sun.adsfinder01.ui.login.LoginViewModel
import com.sun.adsfinder01.ui.register.RegistrationViewModel
import com.sun.adsfinder01.ui.search.SearchResultViewModel
import com.sun.adsfinder01.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }

    viewModel { RegistrationViewModel(get()) }

    viewModel { HomeViewModel(get()) }

    viewModel { SearchViewModel(get()) }

    viewModel { SearchResultViewModel(get()) }
}
