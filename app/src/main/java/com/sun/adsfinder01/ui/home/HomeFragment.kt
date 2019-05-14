package com.sun.adsfinder01.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sun.adsfinder01.R
import com.sun.adsfinder01.data.model.User

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        private const val ARGUMENT_USER = "ARGUMENT_USER"

        fun newInstance(user: User?) = HomeFragment().apply {
            arguments = Bundle().apply {
                user?.let {
                    putParcelable(ARGUMENT_USER, user)
                }
            }
        }
    }
}
