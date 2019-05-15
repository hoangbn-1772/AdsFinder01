package com.sun.adsfinder01.data.repository.api

import com.sun.adsfinder01.data.model.PlaceWrapper
import com.sun.adsfinder01.data.remote.PlaceRemoteDataSource
import io.reactivex.Single

class PlaceRepository(private val placeRemoteDataSource: PlaceRemoteDataSource) : PlaceDataSource {
    override fun getPlaces(author: Int?, placeStatus: String?): Single<PlaceWrapper> {
        return placeRemoteDataSource.getPlaces(author, placeStatus)
    }
}
