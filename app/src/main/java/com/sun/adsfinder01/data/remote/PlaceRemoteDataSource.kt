package com.sun.adsfinder01.data.remote

import com.sun.adsfinder01.data.model.Place
import com.sun.adsfinder01.data.repository.PlaceDataSource
import com.sun.adsfinder01.data.repository.api.ApiService
import io.reactivex.Single

class PlaceRemoteDataSource(private val apiService: ApiService) : PlaceDataSource {

    override fun getPlaces(author: Int?, placeStatus: String?): Single<List<Place>> {
        return apiService.getPlaces(author, placeStatus).map { it.data }
    }
}
