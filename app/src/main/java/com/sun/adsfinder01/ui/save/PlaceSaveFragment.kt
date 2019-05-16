package com.sun.adsfinder01.ui.save

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sun.adsfinder01.R

class PlaceSaveFragment : Fragment(), OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_place_save, container, false)
        view.setOnClickListener(this)
        return view
    }

    override fun onClick(v: View?) {
    }
}
