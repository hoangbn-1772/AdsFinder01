package com.sun.adsfinder01.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sun.adsfinder01.data.model.ApiResponse
import com.sun.adsfinder01.data.model.Place
import com.sun.adsfinder01.data.model.Seeker
import com.sun.adsfinder01.data.repository.PlaceRepository
import com.sun.adsfinder01.ui.home.HomeViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchResultViewModel(private val repository: PlaceRepository) : HomeViewModel(repository) {
    private val compositeDisposable = CompositeDisposable()

    private val _places: MutableLiveData<ApiResponse<List<Place>>> by lazy {
        MutableLiveData<ApiResponse<List<Place>>>()
    }

    val places: LiveData<ApiResponse<List<Place>>>
        get() = _places

    fun findPlace(userId: Int?, seeker: Seeker) {
        compositeDisposable.add(
            repository.findPlaces(userId, seeker)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { places ->
                        _places.value = ApiResponse.onSuccess(places)
                    },
                    { error ->
                        _places.value = ApiResponse.onError(error.toString())
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
