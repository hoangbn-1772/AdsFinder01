package com.sun.adsfinder01.ui.placedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.sun.adsfinder01.R
import com.sun.adsfinder01.data.model.Place
import com.sun.adsfinder01.data.model.User
import com.sun.adsfinder01.ui.contract.ContractFragment
import com.sun.adsfinder01.util.Constants
import kotlinx.android.synthetic.main.fragment_place_detail.floating_call
import kotlinx.android.synthetic.main.fragment_place_detail.floating_contract
import kotlinx.android.synthetic.main.fragment_place_detail.floating_direction
import kotlinx.android.synthetic.main.fragment_place_detail.imagePlace
import kotlinx.android.synthetic.main.fragment_place_detail.imagePrevious
import kotlinx.android.synthetic.main.fragment_place_detail.textAddress
import kotlinx.android.synthetic.main.fragment_place_detail.textDateCreated
import kotlinx.android.synthetic.main.fragment_place_detail.textPlaceAria
import kotlinx.android.synthetic.main.fragment_place_detail.textPlaceHeight
import kotlinx.android.synthetic.main.fragment_place_detail.textPlaceWidth
import kotlinx.android.synthetic.main.fragment_place_detail.textPosterType
import kotlinx.android.synthetic.main.fragment_place_detail.textTotalPrice
import kotlinx.android.synthetic.main.fragment_place_detail.textWallType

class PlaceDetailFragment : Fragment(), OnClickListener {

    private val place by lazy { arguments?.getParcelable(Constants.ARGUMENT_PLACE) as Place }
    private val user by lazy { arguments?.getParcelable(Constants.ARGUMENT_USER) as User }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_place_detail, container, false)
        view.setOnClickListener(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        showPlaceInfo()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imagePrevious -> activity?.supportFragmentManager?.popBackStack()
            R.id.floating_call -> handleCall()
            R.id.floating_contract -> handleContract()
            R.id.floating_direction -> navigate()
        }
    }

    private fun initComponents() {
        imagePrevious.setOnClickListener(this)
        floating_call.setOnClickListener(this)
        floating_contract.setOnClickListener(this)
        floating_direction.setOnClickListener(this)
    }

    private fun showPlaceInfo() {
        showImage()
        showWallType()
        showPosterType()
        showAddress()
        showPlaceSize()
        showDateCreated()
        showPrice()
    }

    private fun showWallType() {
        val content = StringBuilder()
        for (i in 0 until place.wallType?.size!!) {
            content.append(place.wallType!![i].type)
            content.append("\n")
        }
        textWallType.text = content.toString()
    }

    private fun showPosterType() {
        val content = StringBuilder()
        for (i in 0 until place.posterType?.size!!) {
            content.append(place.posterType!![i].type)
            content.append("\n")
        }
        textPosterType.text = content.toString()
    }

    private fun showAddress() {
        val address = StringBuilder()
        address.append(resources.getString(R.string.address))
        address.append(resources.getString(R.string.separate))
        address.append("\t")
        address.append(place.address)
        textAddress?.text = address.toString()
    }

    private fun showPlaceSize() {
        val placeWidth = StringBuilder()
        placeWidth.append(resources.getString(R.string.place_width))
        placeWidth.append(resources.getString(R.string.separate))
        placeWidth.append("\t")
        placeWidth.append(place.width)
        textPlaceWidth?.text = placeWidth.toString()

        val placeHeight = StringBuilder()
        placeHeight.append(resources.getString(R.string.place_width))
        placeHeight.append(resources.getString(R.string.separate))
        placeHeight.append("\t")
        placeHeight.append(place.width)
        textPlaceHeight?.text = placeHeight.toString()

        val placeSize = StringBuilder()
        placeSize.append(resources.getString(R.string.place_size))
        placeSize.append(resources.getString(R.string.separate))
        placeSize.append("\t")
        placeSize.append(place.calculateAria())
        placeSize.append(resources.getString(R.string.place_unit))
        textPlaceAria?.text = placeSize.toString()
    }

    private fun showDateCreated() {
        val date = StringBuilder()
        date.append(resources.getString(R.string.date_created))
        date.append(resources.getString(R.string.separate))
        date.append("\t")
        date.append(place.dateCreated)
        textDateCreated?.text = date.toString()
    }

    private fun showPrice() {
        val price = StringBuilder()
        price.append(resources.getString(R.string.total_price))
        price.append(resources.getString(R.string.separate))
        price.append("\t")
        price.append(place.price?.text.toString())
        price.append("\t")
        price.append(place.price?.unit)
        textTotalPrice?.text = price.toString()
    }

    private fun showImage() {
        imagePlace?.let {
            Glide.with(it)
                .load(place.imageUrl)
                .centerCrop()
                .placeholder(R.drawable.image_empty)
                .error(R.drawable.image_empty)
                .into(it)
        }
    }

    private fun navigate() {
        val desLat = place.lat ?: 0.0
        val desLng = place.lng ?: 0.0

        startActivity(context?.let { DirectionPlaceActivity.getIntent(it, desLat, desLng) })
    }

    private fun handleCall() {
        // Contact with provider
    }

    private fun handleContract() {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.drawer_layout, ContractFragment.newInstance(user, place))
            ?.addToBackStack("")
            ?.commit()
    }

    companion object {

        @JvmStatic
        fun newInstance(user: User?, place: Place?) = PlaceDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(Constants.ARGUMENT_PLACE, place)
                putParcelable(Constants.ARGUMENT_USER, user)
            }
        }
    }
}
