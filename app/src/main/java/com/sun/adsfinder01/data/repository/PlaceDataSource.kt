package com.sun.adsfinder01.data.repository

import com.sun.adsfinder01.data.model.Place
import io.reactivex.Single

interface PlaceDataSource {
    fun getPlaces(author: Int?, placeStatus: String?): Single<List<Place>>
}
