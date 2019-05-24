package com.sun.adsfinder01.data.repository.api

import com.sun.adsfinder01.data.model.Account
import com.sun.adsfinder01.data.model.PlaceWrapper
import com.sun.adsfinder01.data.model.UserWrapper
import io.reactivex.Completable
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
        const val SEARCH_PLACE = "place/search"
        const val LAT = "lat"
        const val LNG = "lng"
        const val POSTER_ID = "id_poster"
        const val WALL_ID = "id_wall"
        const val MAX_WIDTH = "max_width"
        const val MAX_HEIGHT = "max_height"
        const val MAX_PRICE = "max_price"
        const val DISTANCE = "distance"
    }

    @POST(LOGIN)
    fun login(@Body body: Account?): Single<UserWrapper>

    @POST(REGISTER)
    fun register(@Body body: Account?): Single<UserWrapper>

    @GET(PLACE)
    fun getPlaces(@Header(AUTHOR) author: Int?, @Query(CATEGORY) placeStatus: String?): Single<PlaceWrapper>

    @PUT(FAVORITE_PLACE)
    fun savePlace(@Header(AUTHOR) author: Int?, @Query(PLACE_ID) placeId: Int?): Completable

    @DELETE(FAVORITE_PLACE)
    fun removePlace(@Header(AUTHOR) author: Int?, @Query(PLACE_ID) placeId: Int?): Completable

    @GET(SEARCH_PLACE)
    fun findPlaces(
        @Header(AUTHOR) author: Int?,
        @Query(LAT) lat: Double,
        @Query(LNG) lng: Double,
        @Query(POSTER_ID) posterId: String,
        @Query(WALL_ID) wallId: String,
        @Query(MAX_WIDTH) maxWidth: Int,
        @Query(MAX_HEIGHT) maxHeight: Int,
        @Query(MAX_PRICE) maxPrice: Int,
        @Query(DISTANCE) distance: Int
    ): Single<PlaceWrapper>
}
