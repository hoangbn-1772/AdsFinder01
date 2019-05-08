package com.sun.adsfinder01.data.repository.api

import com.sun.adsfinder01.data.model.Account
import com.sun.adsfinder01.data.model.UserWrapper
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    companion object {
        const val LOGIN = "auth/login"
        const val REGISTER = "auth/register"
    }

    @POST(LOGIN)
    fun login(@Body body: Account?): Single<UserWrapper>

    @POST(REGISTER)
    fun register(@Body body: Account?): Single<UserWrapper>
}
