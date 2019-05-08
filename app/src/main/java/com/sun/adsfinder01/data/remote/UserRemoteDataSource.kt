package com.sun.adsfinder01.data.remote

import com.sun.adsfinder01.data.model.Account
import com.sun.adsfinder01.data.model.UserWrapper
import com.sun.adsfinder01.data.repository.UserDataSource
import com.sun.adsfinder01.data.repository.api.ApiService
import io.reactivex.Single

class UserRemoteDataSource(private val apiService: ApiService) : UserDataSource.Remote {

    override fun login(account: Account?): Single<UserWrapper> {
        return apiService.login(account)
    }

    override fun register(account: Account?): Single<UserWrapper> {
        return apiService.register(account)
    }
}
