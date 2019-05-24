package com.sun.adsfinder01.ui.search

import android.os.Bundle
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
import com.sun.adsfinder01.data.model.Seeker
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

class SearchResultFragment : Fragment(), OnClickListener {

    private var user: User? = null

    private var seeker: Seeker? = null

    private val viewModel: SearchResultViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            user = getParcelable(Constants.ARGUMENT_USER)
            seeker = getParcelable(ARGUMENT_SEEKER)
        } ?: run {
            user = User()
            seeker = Seeker("", "")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search_result, container, false)
        view.setOnClickListener(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        user?.id?.let {
            viewModel.findPlace(it, seeker!!)
            showLoading()
            hidePlacesEmpty()
        }
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

    private fun doObserve() {
        viewModel.places.observe(this, Observer { response ->
            handleResponse(response)
            hideLoading()
        })

        viewModel.savePlaceLiveData.observe(this, Observer {
            savePlace(it)
        })

        viewModel.removePlaceLiveData.observe(this, Observer {
            removePlace(it)
        })
    }

    private fun handleResponse(response: ApiResponse<List<Place>>) {
        when (response.status) {
            SUCCESS -> showPlaces(response.data)
            ERROR -> context?.showMessage(response.message)
        }
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

    private fun showPlaces(places: List<Place>?) {
        if (places.isNullOrEmpty()) {
            showPlacesEmpty()
            return
        }

        hidePlacesEmpty()
        val searchAdapter = HomeAdapter(
            places,
            { place -> onClickPlaceItem(place) },
            { place, status -> likePost(place, status) })

        recyclerViewSearch?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchAdapter
        }
    }

    private fun onClickPlaceItem(place: Place) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.drawer_layout, PlaceDetailFragment.newInstance(user, place))
            ?.addToBackStack("")
            ?.commit()
    }

    private fun likePost(place: Place, status: Boolean) {
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
        recyclerViewSearch?.visibility = View.GONE
        imageEmpty?.visibility = View.VISIBLE
    }

    private fun hidePlacesEmpty() {
        recyclerViewSearch?.visibility = View.VISIBLE
        imageEmpty?.visibility = View.GONE
    }

    private fun showLoading() {
        progressLoading?.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressLoading?.visibility = View.GONE
    }

    companion object {
        const val ARGUMENT_SEEKER = "ARGUMENT_SEEKER"

        fun newInstance(user: User?, seeker: Seeker) = SearchResultFragment().apply {
            arguments = Bundle().apply {
                putParcelable(Constants.ARGUMENT_USER, user)
                putParcelable(ARGUMENT_SEEKER, seeker)
            }
        }
    }
}
