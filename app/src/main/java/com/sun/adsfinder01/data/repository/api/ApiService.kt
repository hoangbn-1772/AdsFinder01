package com.sun.adsfinder01.data.repository.api

import com.sun.adsfinder01.data.model.Account
import com.sun.adsfinder01.data.model.PlaceWrapper
import com.sun.adsfinder01.data.model.UserWrapper
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val LOGIN = "auth/login"
        const val REGISTER = "auth/register"
        const val PLACE = "place"
        const val AUTHOR = "Authorization"
        const val CATEGORY = "category"
        const val FAVORITE_PLACE = "place/favorite"
        const val PLACE_ID = "id_place"
    }

    @POST(LOGIN)
    fun login(@Body body: Account?): Single<UserWrapper>

    @POST(REGISTER)
    fun register(@Body body: Account?): Single<UserWrapper>

    @GET(PLACE)
    fun getPlaces(@Header(AUTHOR) author: Int?, @Query(CATEGORY) placeStatus: String?): Single<PlaceWrapper>

    @PUT(FAVORITE_PLACE)
    fun savePlace(@Header(AUTHOR) author: Int?, @Query(PLACE_ID) placeId: Int?): Single<PlaceWrapper>

    @DELETE(FAVORITE_PLACE)
    fun removePlace(@Header(AUTHOR) author: Int?, @Query(PLACE_ID) placeId: Int?): Single<PlaceWrapper>
}
