package com.sun.adsfinder01.data.model.base

import com.google.gson.annotations.SerializedName

open class DataWrapperBase<T> {
    @SerializedName(SUCCESS) val success: Boolean = false

    @SerializedName(CODE) val code: String = ""

    @SerializedName(MESSAGE) val message: String = ""

    @SerializedName(DATA) val data: T? = null

    companion object {
        const val SUCCESS = "success"
        const val CODE = "code"
        const val MESSAGE = "message"
        const val DATA = "data"
    }
}
