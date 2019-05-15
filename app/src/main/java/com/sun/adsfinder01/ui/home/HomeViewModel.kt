package com.sun.adsfinder01.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sun.adsfinder01.data.model.ApiResponse
import com.sun.adsfinder01.data.model.Place
import com.sun.adsfinder01.data.repository.api.PlaceRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val repository: PlaceRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _placeLiveData: MutableLiveData<ApiResponse<List<Place>>> by lazy {
        MutableLiveData<ApiResponse<List<Place>>>()
    }

    val placeLiveData: LiveData<ApiResponse<List<Place>>>
        get() = _placeLiveData

    fun requestPlaces(userId: Int?) {
        compositeDisposable.add(
            repository.getPlaces(userId, LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { placeWrapper ->
                    placeWrapper.data
                }
                .subscribe(
                    { places ->
                        _placeLiveData.postValue(ApiResponse.onSuccess(places))
                    },
                    { error ->
                        _placeLiveData.postValue(ApiResponse.onError(error.toString()))
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    companion object {
        const val LATEST = "latest"
    }
}
