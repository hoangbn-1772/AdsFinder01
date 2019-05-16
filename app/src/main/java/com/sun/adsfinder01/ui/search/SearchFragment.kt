package com.sun.adsfinder01.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import com.sun.adsfinder01.R
import kotlinx.android.synthetic.main.fragment_search.buttonSearch
import kotlinx.android.synthetic.main.fragment_search.imagePrevious
import kotlinx.android.synthetic.main.fragment_search.root_search
import kotlinx.android.synthetic.main.fragment_search.seekBarHeight
import kotlinx.android.synthetic.main.fragment_search.seekBarPrice
import kotlinx.android.synthetic.main.fragment_search.seekBarWidth
import kotlinx.android.synthetic.main.fragment_search.textHeightChoose
import kotlinx.android.synthetic.main.fragment_search.textPriceChoose
import kotlinx.android.synthetic.main.fragment_search.textWidthChoose

class SearchFragment : Fragment(), OnClickListener, OnSeekBarChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        view.setOnClickListener(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imagePrevious -> activity?.supportFragmentManager?.popBackStack()
            R.id.buttonSearch -> {
                // Search place
            }
        }
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        when (seekBar?.id) {
            R.id.seekBarWidth -> textWidthChoose.text = progress.toString()
            R.id.seekBarHeight -> textHeightChoose.text = progress.toString()
            R.id.seekBarPrice -> showPriceSearchLimit(progress)
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    private fun initComponents() {
        root_search.setOnClickListener(this)
        imagePrevious.setOnClickListener(this)
        buttonSearch.setOnClickListener(this)
        seekBarWidth.setOnSeekBarChangeListener(this)
        seekBarHeight.setOnSeekBarChangeListener(this)
        seekBarPrice.setOnSeekBarChangeListener(this)
    }

    private fun showPriceSearchLimit(price: Int) {
        val priceSearch = StringBuilder()
        priceSearch.append(price)
        priceSearch.append(resources.getString(R.string.place_unit))
        textPriceChoose.text = priceSearch.toString()
    }
}
