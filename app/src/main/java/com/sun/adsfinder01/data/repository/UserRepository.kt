package com.sun.adsfinder01.data.repository

import com.sun.adsfinder01.data.model.Account
import com.sun.adsfinder01.data.model.UserWrapper
import com.sun.adsfinder01.data.remote.UserRemoteDataSource
import io.reactivex.Single

class UserRepository(private val userRemoteDataSource: UserRemoteDataSource) : UserDataSource.Remote {

    override fun login(account: Account?): Single<UserWrapper> {
        return userRemoteDataSource.login(account)
    }

    override fun register(account: Account?): Single<UserWrapper> {
        return userRemoteDataSource.register(account)
    }
}
