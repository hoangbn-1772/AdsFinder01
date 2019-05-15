package com.sun.adsfinder01.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sun.adsfinder01.data.model.base.DataWrapperBase
import kotlinx.android.parcel.Parcelize

class PlaceWrapper : DataWrapperBase<List<Place>>()

@Parcelize
data class Place(
    @SerializedName(ID) val id: Int?,
    @SerializedName(ADDRESS) val address: String?,
    @SerializedName(IMAGE_URL) val imageUrl: String?,
    @SerializedName(LAT) val lat: Double?,
    @SerializedName(LNG) val lng: Double?,
    @SerializedName(DESCRIPTION) val description: String?,
    @SerializedName(WIDTH) val width: Double?,
    @SerializedName(HEIGHT) val height: Double?,
    @SerializedName(PRICE) val price: Price?,
    @SerializedName(CONSTRUCTION_PRICE) val constructionPrice: Double?,
    @SerializedName(WALL_TYPE) val wallType: List<WallType>?,
    @SerializedName(POSTER_TYPE) val posterType: List<PosterType>?,
    @SerializedName(DATE_CREATED) val dateCreated: String?
) : Parcelable {

    fun calculateAria(): Double? {
        return this.height?.let { width?.times(it) }
    }

    companion object {
        const val ID = "id"

        const val ADDRESS = "address"

        const val IMAGE_URL = "imageUrl"

        const val LAT = "latitude"

        const val LNG = "longitude"

        const val DESCRIPTION = "description"

        const val WIDTH = "width"

        const val HEIGHT = "height"

        const val PRICE = "price"

        const val CONSTRUCTION_PRICE = "constructionPrice"

        const val WALL_TYPE = "wallType"

        const val POSTER_TYPE = "posterType"

        const val DATE_CREATED = "dateCreated"
    }
}

@Parcelize
data class PosterType(@SerializedName(ID) val id: Int?, @SerializedName(TYPE) val type: String?) : Parcelable {

    companion object {
        const val ID = "id"

        const val TYPE = "type"
    }
}

@Parcelize
data class WallType(@SerializedName(ID) val id: Int?, @SerializedName(TYPE) val type: String?) : Parcelable {

    companion object {
        const val ID = "id"

        const val TYPE = "type"
    }
}

@Parcelize
data class Price(
    @SerializedName(VALUE) val value: Int?,
    @SerializedName(TEXT) val text: String?,
    @SerializedName(UNIT) val unit: String?
) : Parcelable {

    companion object {
        const val VALUE = "value"

        const val TEXT = "text"

        const val UNIT = "unit"
    }
}
