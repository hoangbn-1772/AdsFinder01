package com.sun.adsfinder01.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.sun.adsfinder01.R

class DataBindingUtil {

    companion object {

        private const val SET_IMAGE_PLACE = "setPlaceImage"

        @JvmStatic
        @BindingAdapter(SET_IMAGE_PLACE)
        fun ImageView.setImagePlace(url: String?) {
            Glide.with(this)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.image_empty)
                .error(R.drawable.image_empty)
                .into(this)
        }
    }
}
