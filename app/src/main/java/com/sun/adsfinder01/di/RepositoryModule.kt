package com.sun.adsfinder01.di

import com.sun.adsfinder01.data.remote.UserRemoteDataSource
import com.sun.adsfinder01.data.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { UserRemoteDataSource(get()) }

    single { UserRepository(get()) }
}
