package com.sun.adsfinder01.util

import android.content.res.Resources
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.sun.adsfinder01.R

class DataBindingUtil {

    companion object {

        private const val SET_CONTENT_PLACE = "setPlaceContent"

        private const val SET_SIZE_PLACE = "setPlaceSize"

        private const val SET_IMAGE_PLACE = "setPlaceImage"

        @JvmStatic
        @BindingAdapter(SET_CONTENT_PLACE)
        fun TextView.setContent(content: String?) {
            this.text = content ?: ""
        }

        @JvmStatic
        @BindingAdapter(SET_SIZE_PLACE)
        fun TextView.setSizePlace(sizePlace: String?) {
            this.text = sizePlace ?: Resources.getSystem().getString(R.string.updating)
        }

        @JvmStatic
        @BindingAdapter(SET_IMAGE_PLACE)
        fun ImageView.setImagePlace(url: String?) {
            if (url != null) {
                Glide.with(this)
                    .load(url)
                    .centerCrop()
                    .placeholder(R.drawable.image_empty)
                    .into(this)
            }
        }
    }
}
