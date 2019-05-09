package com.sun.adsfinder01.data.model

import com.sun.adsfinder01.data.model.NetworkStatus.INVALID

class ApiResponse<T>(
    val status: NetworkStatus,
    val data: T?,
    val error: Throwable?
) {

    companion object {
        fun <T> loading() = ApiResponse<T>(NetworkStatus.LOADING, null, null)

        fun <T> invalidate() = ApiResponse<T>(INVALID, null, null)

        fun <T> success(data: T) =
            ApiResponse<T>(NetworkStatus.SUCCESS, data, null)

        fun <T> error(error: Throwable) =
            ApiResponse<T>(NetworkStatus.ERROR, null, error)
    }
}
