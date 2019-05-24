package com.sun.adsfinder01.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sun.adsfinder01.data.model.base.DataWrapperBase
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName(ID) val id: Int? = null,
    @SerializedName(FIRST_NAME) val firstName: String? = null,
    @SerializedName(LAST_NAME) val lastName: String? = null,
    @SerializedName(PHONE) val phone: String? = null,
    @SerializedName(EMAIL) val email: String? = null,
    @SerializedName(IMAGE_URL) val imageUrl: String? = null,
    @SerializedName(BIRTH_DATE) val birthDate: String? = null,
    @SerializedName(GENDER) var gender: String? = null,
    @SerializedName(ROLE) var role: String? = null
) : Parcelable {

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
