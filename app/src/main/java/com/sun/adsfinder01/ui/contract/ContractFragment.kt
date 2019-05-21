package com.sun.adsfinder01.ui.contract

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sun.adsfinder01.R
import com.sun.adsfinder01.data.model.Place
import com.sun.adsfinder01.data.model.User
import com.sun.adsfinder01.util.Constants
import kotlinx.android.synthetic.main.fragment_contract.buttonContract
import kotlinx.android.synthetic.main.fragment_contract.textEndDate
import kotlinx.android.synthetic.main.fragment_contract.textPlaceAddress
import kotlinx.android.synthetic.main.fragment_contract.textPlaceId
import kotlinx.android.synthetic.main.fragment_contract.textStartDate
import kotlinx.android.synthetic.main.fragment_contract.textUserId
import kotlinx.android.synthetic.main.fragment_contract.textUserName

class ContractFragment : Fragment(), OnClickListener {

    private val user by lazy { arguments?.getParcelable(Constants.ARGUMENT_USER) as User }

    private val place by lazy { arguments?.getParcelable(Constants.ARGUMENT_PLACE) as Place }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_contract, container, false)
        view.setOnClickListener(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
    }

    override fun onClick(v: View?) {
    }

    private fun initComponents() {
        textStartDate.setOnClickListener(this)
        textEndDate.setOnClickListener(this)
        buttonContract.setOnClickListener(this)

        showCustomerInfo()
        showPlaceInfo()
    }

    private fun showCustomerInfo() {
        textUserId.text = user.id.toString()

        val customerName = StringBuilder()
        customerName.append(user.firstName)
        customerName.append("\t")
        customerName.append(user.lastName)
        textUserName.text = customerName.toString()
    }

    private fun showPlaceInfo() {
        textPlaceAddress.text = place.address
        textPlaceId.text = place.id.toString()
    }

    companion object {

        fun newInstance(user: User?, place: Place) = ContractFragment().apply {
            arguments = Bundle().apply {
                putParcelable(Constants.ARGUMENT_PLACE, place)
                putParcelable(Constants.ARGUMENT_USER, user)
            }
        }
    }
}
