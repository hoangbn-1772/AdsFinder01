package com.sun.adsfinder01.ui.register

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sun.adsfinder01.R
import kotlinx.android.synthetic.main.activity_registration.buttonRegisterAccount
import kotlinx.android.synthetic.main.activity_registration.toolbarRegistrationScreen

class RegistrationActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        initComponents()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonRegisterAccount -> handleRegistrationAccount()
        }
    }

    private fun initComponents() {
        initToolbar()
        buttonRegisterAccount.setOnClickListener(this)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbarRegistrationScreen)
        supportActionBar?.title = resources.getString(R.string.register)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbarRegistrationScreen.setNavigationOnClickListener {
            finish()
        }
    }

    private fun handleRegistrationAccount() {
        // handle registration account
    }
}
