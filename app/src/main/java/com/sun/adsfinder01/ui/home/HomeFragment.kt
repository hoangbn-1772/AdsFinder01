package com.sun.adsfinder01.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
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
import com.sun.adsfinder01.util.ContextExtension.showMessage
import kotlinx.android.synthetic.main.fragment_home.imageEmpty
import kotlinx.android.synthetic.main.fragment_home.progressLoading
import kotlinx.android.synthetic.main.fragment_home.recyclerViewHome
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()

    private val user by lazy { arguments?.get(ARGUMENT_USER) as User }

    private val homeAdapter by lazy {
        HomeAdapter(
            ArrayList(),
            { place -> onClickPlaceItem(place) },
            { place, status -> handleLikePost(place, status) }
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        requestPlaces()
        doObserve()
    }

    private fun onClickPlaceItem(place: Place?) {
        // Item click
        context?.showMessage(place?.address!!)
    }

    private fun handleLikePost(place: Place?, status: Boolean) {
        // Icon save click
        context?.showMessage(place?.dateCreated!!)
    }

    private fun initComponents() {
        recyclerViewHome?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = homeAdapter
        }
    }

    private fun doObserve() {
        homeViewModel.placeLiveData.observe(this, Observer { response ->
            handleResponse(response)
        })
    }

    private fun requestPlaces() {
        homeViewModel.requestPlaces(user.id)
    }

    private fun handleResponse(response: ApiResponse<List<Place>>) {
        when (response.status) {
            SUCCESS -> updatePlaces(response.data)
            ERROR -> context?.showMessage(response.message)
        }
    }

    private fun updatePlaces(places: List<Place>?) = when {
        places != null -> {
            homeAdapter.updatePlaces(places)
            showPlaces()
        }
        else -> hidePlaces()
    }

    private fun showPlaces() {
        recyclerViewHome.visibility = View.VISIBLE
        progressLoading.visibility = View.GONE
        imageEmpty.visibility = View.GONE
    }

    private fun hidePlaces() {
        recyclerViewHome.visibility = View.GONE
        progressLoading.visibility = View.GONE
        imageEmpty.visibility = View.VISIBLE
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
