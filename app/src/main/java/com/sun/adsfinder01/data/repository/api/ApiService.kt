package com.sun.adsfinder01.data.repository.api

import com.sun.adsfinder01.data.model.Account
import com.sun.adsfinder01.data.model.UserWrapper
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    companion object {
        const val LOGIN = "auth/login"

        const val BASE_URL = "https://spring-boot-wall-tags.herokuapp.com/adsharingspace/"

        const val CONTENT_TYPE = "Content-Type: application/json"
    }

    @Headers(CONTENT_TYPE)
    @POST(LOGIN)
    fun login(
        @Body body: Account?
    ): Single<UserWrapper>
}
