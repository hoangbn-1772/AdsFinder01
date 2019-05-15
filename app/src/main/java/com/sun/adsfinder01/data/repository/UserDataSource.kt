package com.sun.adsfinder01.data.repository

import com.sun.adsfinder01.data.model.Account
import com.sun.adsfinder01.data.model.UserWrapper
import io.reactivex.Single

interface UserDataSource {
    interface Remote {

        fun login(account: Account?): Single<UserWrapper>

        fun register(account: Account?): Single<UserWrapper>
    }
}
