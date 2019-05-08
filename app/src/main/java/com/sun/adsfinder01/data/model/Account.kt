package com.sun.adsfinder01.data.model

import com.google.gson.annotations.SerializedName

data class Account(
    @SerializedName(User.PHONE_OR_EMAIL) private val phoneOrEmail: String?,
    @SerializedName(User.PASSWORD) private val password: String?
)
