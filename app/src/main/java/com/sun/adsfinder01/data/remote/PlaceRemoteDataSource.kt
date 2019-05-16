package com.sun.adsfinder01.data.remote

import com.sun.adsfinder01.data.model.Place
import com.sun.adsfinder01.data.model.Seeker
import com.sun.adsfinder01.data.repository.PlaceDataSource
import com.sun.adsfinder01.data.repository.api.ApiService
import io.reactivex.Completable
import io.reactivex.Single

class PlaceRemoteDataSource(private val apiService: ApiService) : PlaceDataSource {

    override fun getPlaces(author: Int?, placeStatus: String?): Single<List<Place>> {
        return apiService.getPlaces(author, placeStatus).map { it.data }
    }

    override fun savePlace(author: Int?, placeId: Int?): Completable {
        return apiService.savePlace(author, placeId)
    }

    override fun removePlace(author: Int?, placeId: Int?): Completable {
        return apiService.removePlace(author, placeId)
    }

    override fun findPlaces(author: Int?, seeker: Seeker): Single<List<Place>> {
        return apiService.findPlaces(
            author,
            seeker.lat,
            seeker.lng,
            seeker.posterId,
            seeker.wallId,
            seeker.placeWidth,
            seeker.placeHeight,
            seeker.priceLimit,
            seeker.distance
        ).map { it.data }
    }
}
