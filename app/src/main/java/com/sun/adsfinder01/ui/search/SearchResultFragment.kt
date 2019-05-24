package com.sun.adsfinder01.ui.search

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sun.adsfinder01.R
import com.sun.adsfinder01.data.model.ApiResponse
import com.sun.adsfinder01.data.model.NetworkStatus.ERROR
import com.sun.adsfinder01.data.model.NetworkStatus.SUCCESS
import com.sun.adsfinder01.data.model.Place
import com.sun.adsfinder01.data.model.User
import com.sun.adsfinder01.ui.home.HomeAdapter
import com.sun.adsfinder01.ui.placedetail.PlaceDetailFragment
import com.sun.adsfinder01.util.Constants
import com.sun.adsfinder01.util.ContextExtension.showMessage
import kotlinx.android.synthetic.main.fragment_search_result.imageEmpty
import kotlinx.android.synthetic.main.fragment_search_result.imagePrevious
import kotlinx.android.synthetic.main.fragment_search_result.progressLoading
import kotlinx.android.synthetic.main.fragment_search_result.recyclerViewSearch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

class SearchResultFragment : Fragment(), OnClickListener {

    private val user by lazy { arguments?.getParcelable<User>(Constants.ARGUMENT_USER) }

    private val places by lazy { arguments?.getParcelableArrayList<Place>(ARGUMENT_PLACES) }

    private val viewModel: SearchResultViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search_result, container, false)
        view.setOnClickListener(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        showPlaces()
        doObserve()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imagePrevious -> activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun initComponents() {
        imagePrevious.setOnClickListener(this)
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

    private fun doObserve() {
        viewModel.savePlaceLiveData.observe(this, Observer {
            savePlace(it)
        })

        viewModel.removePlaceLiveData.observe(this, Observer {
            removePlace(it)
        })
    }

    private fun onClickPlaceItem(place: Place) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.drawer_layout, PlaceDetailFragment.newInstance(user, place))
            ?.addToBackStack("")
            ?.commit()
    }

    private fun handleLikePost(place: Place, status: Boolean) {
        when {
            status -> {
                viewModel.savePlace(user?.id, place.id)
            }
            else -> {
                viewModel.removePlace(user?.id, place.id)
            }
        }
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

    private fun savePlace(response: ApiResponse<Boolean>) {
        when (response.status) {
            SUCCESS -> context?.showMessage(resources.getString(R.string.save_place_success))
            ERROR -> context?.showMessage(response.message)
        }
    }

    private fun removePlace(response: ApiResponse<Boolean>) {
        when (response.status) {
            SUCCESS -> context?.showMessage(resources.getString(R.string.remove_place_success))
            ERROR -> context?.showMessage(response.message)
        }
    }

    companion object {
        const val ARGUMENT_PLACES = "ARGUMENT_PLACES"

        fun newInstance(user: User?, places: List<Place>?) = SearchResultFragment().apply {
            arguments = Bundle().apply {
                putParcelable(Constants.ARGUMENT_USER, user)
                putParcelableArrayList(ARGUMENT_PLACES, places as ArrayList<out Parcelable>)
            }
        }
    }
}
