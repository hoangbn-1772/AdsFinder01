package com.sun.adsfinder01.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sun.adsfinder01.data.model.ApiResponse
import com.sun.adsfinder01.data.model.Place
import com.sun.adsfinder01.data.repository.PlaceRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

open class HomeViewModel(private val repository: PlaceRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _placeLiveData: MutableLiveData<ApiResponse<List<Place>>> by lazy {
        MutableLiveData<ApiResponse<List<Place>>>()
    }

    val placeLiveData: LiveData<ApiResponse<List<Place>>>
        get() = _placeLiveData

    private val _savePlaceLiveData: MutableLiveData<ApiResponse<Boolean>> by lazy {
        MutableLiveData<ApiResponse<Boolean>>()
    }

    val savePlaceLiveData: LiveData<ApiResponse<Boolean>>
        get() = _savePlaceLiveData

    private val _removePlaceLiveData: MutableLiveData<ApiResponse<Boolean>> by lazy {
        MutableLiveData<ApiResponse<Boolean>>()
    }

    val removePlaceLiveData: LiveData<ApiResponse<Boolean>>
        get() = _removePlaceLiveData

    fun requestPlaces(userId: Int?) {
        compositeDisposable.add(
            repository.getPlaces(userId, LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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

    fun savePlace(userId: Int?, placeId: Int?) {
        compositeDisposable.add(
            repository.savePlace(userId, placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _savePlaceLiveData.value = ApiResponse.onSuccess(true)
                    },
                    {
                        _savePlaceLiveData.value = ApiResponse.onError(it.toString())
                    }
                )
        )
    }

    fun removePlace(userId: Int?, placeId: Int?) {
        compositeDisposable.add(
            repository.removePlace(userId, placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _removePlaceLiveData.value = ApiResponse.onSuccess(true)
                    },
                    { error ->
                        _removePlaceLiveData.value = ApiResponse.onError(error.toString())
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
