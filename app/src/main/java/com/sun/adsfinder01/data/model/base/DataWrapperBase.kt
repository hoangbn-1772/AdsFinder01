package com.sun.adsfinder01.data.model.base

import com.google.gson.annotations.SerializedName

open class DataWrapperBase<T> {
    @SerializedName(SUCCESS) var success: Boolean? = false

    @SerializedName(CODE) var code: Int? = 0

    @SerializedName(MESSAGE) var message: String? = ""

    @SerializedName(DATA) var data: T? = null

    companion object {
        const val SUCCESS = "success"
        const val CODE = "code"
        const val MESSAGE = "message"
        const val DATA = "data"
    }
}
