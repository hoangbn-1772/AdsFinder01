package com.sun.adsfinder01.data.remote

import com.sun.adsfinder01.data.model.PlaceWrapper
import com.sun.adsfinder01.data.repository.api.ApiService
import com.sun.adsfinder01.data.repository.api.PlaceDataSource
import io.reactivex.Single

class PlaceRemoteDataSource(private val apiService: ApiService) : PlaceDataSource {
    override fun getPlaces(author: Int?, placeStatus: String?): Single<PlaceWrapper> {
        return apiService.getPlaces(author, placeStatus)
    }
}
