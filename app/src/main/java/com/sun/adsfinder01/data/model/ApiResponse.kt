package com.sun.adsfinder01.data.model

import com.sun.adsfinder01.data.model.NetworkStatus.ERROR
import com.sun.adsfinder01.data.model.NetworkStatus.INVALID
import com.sun.adsfinder01.data.model.NetworkStatus.SUCCESS

class ApiResponse<T>(
    val status: NetworkStatus,
    val data: T?,
    val message: String
) {

    companion object {

        fun <T> invalidInput(msg: String) = ApiResponse<T>(INVALID, null, msg)

        fun <T> onSuccess(data: T?) = ApiResponse(SUCCESS, data, "")

        fun <T> onError(msg: String) = ApiResponse<T>(ERROR, null, msg)
    }
}
