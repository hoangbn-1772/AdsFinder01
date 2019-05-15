package com.sun.adsfinder01.data.repository.api

import com.sun.adsfinder01.data.model.PlaceWrapper
import io.reactivex.Single

interface PlaceDataSource {
    fun getPlaces(author: Int?, placeStatus: String?): Single<PlaceWrapper>
}
