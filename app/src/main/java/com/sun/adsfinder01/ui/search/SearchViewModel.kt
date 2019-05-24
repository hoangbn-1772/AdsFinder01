package com.sun.adsfinder01.ui.search

import androidx.lifecycle.ViewModel
import com.mapbox.mapboxsdk.geometry.LatLng
import com.sun.adsfinder01.data.repository.PlaceRepository

class SearchViewModel(private val repository: PlaceRepository) : ViewModel() {

    var pointSearch = LatLng(0.0, 0.0)

    var placeNameSearch: String? = null
}
