package com.sun.adsfinder01.data.repository

import com.sun.adsfinder01.data.model.Place
import com.sun.adsfinder01.data.model.Seeker
import io.reactivex.Completable
import io.reactivex.Single

interface PlaceDataSource {
    fun getPlaces(author: Int?, placeStatus: String?): Single<List<Place>>

    fun savePlace(author: Int?, placeId: Int?): Completable

    fun removePlace(author: Int?, placeId: Int?): Completable

    fun findPlaces(author: Int?, seeker: Seeker): Single<List<Place>>
}
