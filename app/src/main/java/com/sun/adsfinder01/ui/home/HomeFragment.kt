package com.sun.adsfinder01.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sun.adsfinder01.R
import com.sun.adsfinder01.data.model.ApiResponse
import com.sun.adsfinder01.data.model.NetworkStatus.ERROR
import com.sun.adsfinder01.data.model.NetworkStatus.SUCCESS
import com.sun.adsfinder01.data.model.Place
import com.sun.adsfinder01.data.model.User
import com.sun.adsfinder01.databinding.FragmentHomeBinding
import com.sun.adsfinder01.util.ContextExtension.showMessage
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()

    private lateinit var binding: FragmentHomeBinding

    private val user by lazy { arguments?.get(ARGUMENT_USER) as User }

    private val adapter by lazy {
        HomeAdapter(
            ArrayList(),
            { place -> onClickPlaceItem(place) },
            { place, status -> handleLikePost(place, status) }
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        binding.recyclerViewHome.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewHome.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestPlace()
        doObserve()
    }

    private fun onClickPlaceItem(place: Place?) {
        // Item click
    }

    private fun handleLikePost(place: Place?, status: Boolean) {
        // Icon save click
    }

    private fun doObserve() {
        homeViewModel.placeLiveData.observe(this, Observer { response ->
            handleResponse(response)
        })
    }

    private fun requestPlace() {
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
            adapter.updatePlaces(places)
            showPlaces(true)
        }
        else -> showPlaces(false)
    }

    private fun showPlaces(isShow: Boolean) = when {
        isShow -> {
            binding.progressLoading.visibility = View.GONE
            binding.imageEmpty.visibility = View.GONE
        }
        else -> {
            binding.recyclerViewHome.visibility = View.GONE
            binding.imageEmpty.visibility = View.VISIBLE
        }
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
