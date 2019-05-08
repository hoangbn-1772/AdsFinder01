package com.sun.adsfinder01.data.model

import com.sun.adsfinder01.data.model.EnumStatus.INVALID

class ApiResponse<T>(
    val status: EnumStatus,
    val data: T?,
    val error: Throwable?
) {

    companion object {
        fun <T> loading() = ApiResponse<T>(EnumStatus.LOADING, null, null)

        fun <T> invalidate() = ApiResponse<T>(INVALID, null, null)

        fun <T> success(data: T) =
            ApiResponse<T>(EnumStatus.SUCCESS, data, null)

        fun <T> error(error: Throwable) =
            ApiResponse<T>(EnumStatus.ERROR, null, error)
    }
}
