package com.sun.adsfinder01.data.repository

import com.sun.adsfinder01.data.model.Place
import com.sun.adsfinder01.data.model.Seeker
import com.sun.adsfinder01.data.remote.PlaceRemoteDataSource
import io.reactivex.Completable
import io.reactivex.Single

class PlaceRepository(private val placeRemoteDataSource: PlaceRemoteDataSource) :
    PlaceDataSource {

    override fun getPlaces(author: Int?, placeStatus: String?): Single<List<Place>> {
        return placeRemoteDataSource.getPlaces(author, placeStatus)
    }

    override fun savePlace(author: Int?, placeId: Int?): Completable {
        return placeRemoteDataSource.savePlace(author, placeId)
    }

    override fun removePlace(author: Int?, placeId: Int?): Completable {
        return placeRemoteDataSource.removePlace(author, placeId)
    }

    override fun findPlaces(author: Int?, seeker: Seeker): Single<List<Place>> {
        return placeRemoteDataSource.findPlaces(author, seeker)
    }
}
