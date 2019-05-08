package com.sun.adsfinder01.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MainAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    var mFragments: MutableList<Fragment> = arrayListOf()

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    fun addFragment(fragment: Fragment) {
        mFragments.add(fragment)
    }
}
