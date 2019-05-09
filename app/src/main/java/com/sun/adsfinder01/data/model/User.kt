package com.sun.adsfinder01.data.model

import com.google.gson.annotations.SerializedName
import com.sun.adsfinder01.data.model.base.DataWrapperBase

data class User(
    @SerializedName(ID) val id: Int,
    @SerializedName(FIRST_NAME) val firstName: String,
    @SerializedName(LAST_NAME) val lastName: String,
    @SerializedName(PHONE) val phone: String,
    @SerializedName(EMAIL) val email: String,
    @SerializedName(IMAGE_URL) val imageUrl: String,
    @SerializedName(BIRTH_DATE) val birthDate: String,
    @SerializedName(GENDER) var gender: String,
    @SerializedName(ROLE) var role: String
) {

    companion object {
        const val ID = "id"
        const val FIRST_NAME = "firstName"
        const val LAST_NAME = "lastName"
        const val PHONE = "phone"
        const val EMAIL = "email"
        const val IMAGE_URL = "imageUrl"
        const val BIRTH_DATE = "birthDate"
        const val GENDER = "gender"
        const val ROLE = "role"
        const val PASSWORD = "password"
        const val PHONE_OR_EMAIL = "phoneOrEmail"
    }
}

class UserWrapper : DataWrapperBase<User>()
