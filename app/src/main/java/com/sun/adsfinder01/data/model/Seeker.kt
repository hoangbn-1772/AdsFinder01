package com.sun.adsfinder01.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Seeker(
    val posterId: String,
    val wallId: String,
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val placeWidth: Int = 0,
    val placeHeight: Int = 0,
    val priceLimit: Int = 0,
    val distance: Int = 10
) : Parcelable
