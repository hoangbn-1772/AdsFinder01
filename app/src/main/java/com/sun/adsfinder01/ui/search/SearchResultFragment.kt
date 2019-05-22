package com.sun.adsfinder01.ui.search

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sun.adsfinder01.R
import com.sun.adsfinder01.data.model.Place
import com.sun.adsfinder01.ui.home.HomeAdapter
import kotlinx.android.synthetic.main.fragment_search_result.imageEmpty
import kotlinx.android.synthetic.main.fragment_search_result.progressLoading
import kotlinx.android.synthetic.main.fragment_search_result.recyclerViewSearch
import java.util.ArrayList

class SearchResultFragment : Fragment(), OnClickListener {

    private val places by lazy { arguments?.getParcelableArrayList<Place>(ARGUMENT_PLACES) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search_result, container, false)
        view.setOnClickListener(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showPlaces()
    }

    override fun onClick(v: View?) {
    }

    private fun showPlaces() {
        when {
            places.isNullOrEmpty() -> showPlacesEmpty()
            else -> {
                hidePlacesEmpty()

                val searchAdapter = HomeAdapter(
                    places!!,
                    { place -> onClickPlaceItem(place) },
                    { place, status -> handleLikePost(place, status) })

                recyclerViewSearch?.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = searchAdapter
                }
            }
        }
    }

    private fun onClickPlaceItem(place: Place) {
        // place onClick
    }

    private fun handleLikePost(place: Place, status: Boolean) {
        // save place
    }

    private fun showPlacesEmpty() {
        progressLoading?.visibility = View.GONE
        recyclerViewSearch?.visibility = View.GONE
        imageEmpty?.visibility = View.VISIBLE
    }

    private fun hidePlacesEmpty() {
        progressLoading?.visibility = View.GONE
        recyclerViewSearch?.visibility = View.VISIBLE
        imageEmpty?.visibility = View.GONE
    }

    companion object {
        const val ARGUMENT_PLACES = "ARGUMENT_PLACES"

        fun newInstance(places: List<Place>?) = SearchResultFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList(ARGUMENT_PLACES, places as ArrayList<out Parcelable>)
            }
        }
    }
}
