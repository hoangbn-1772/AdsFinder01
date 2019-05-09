package com.sun.adsfinder01.ui.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.sun.adsfinder01.R
import com.sun.adsfinder01.data.model.ApiResponse
import com.sun.adsfinder01.data.model.NetworkStatus.ERROR
import com.sun.adsfinder01.data.model.NetworkStatus.INVALID
import com.sun.adsfinder01.data.model.NetworkStatus.SUCCESS
import com.sun.adsfinder01.data.model.User
import com.sun.adsfinder01.util.Constants
import com.sun.adsfinder01.util.Global
import kotlinx.android.synthetic.main.activity_login.buttonLogin
import kotlinx.android.synthetic.main.activity_login.buttonRegister
import kotlinx.android.synthetic.main.activity_login.editUserEmail
import kotlinx.android.synthetic.main.activity_login.editUserPassword
import kotlinx.android.synthetic.main.activity_login.progressLogin
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initComponent()

        doObserve()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonRegister -> handleRegister()
            R.id.buttonLogin -> handleLogin()
        }
    }

    private fun doObserve() {
        viewModel.user.observe(this, Observer { response ->
            handleResponse(response)
        })

        viewModel.emailOrPassword.observe(this, Observer { status ->
            handleEmailAndPassword(status)
        })
    }

    private fun initComponent() {
        buttonRegister?.setOnClickListener(this)
        buttonLogin?.setOnClickListener(this)
    }

    private fun handleRegister() {
        //TODO: Register
    }

    private fun handleLogin() {
        enableLogin(false)
        showProgress(true)

        val emailInput = editUserEmail?.text.toString()
        val passwordInput = editUserPassword?.text.toString()
        viewModel.doLogin(emailInput, passwordInput)
    }

    private fun handleEmailAndPassword(status: String) {
        when (status) {
            Constants.EMAIL_EMPTY -> notifyInputInvalid(resources.getString(R.string.email_empty))
            Constants.EMAIL_SYNTAX_ERROR -> notifyInputInvalid(resources.getString(R.string.email_syntax_error))
            Constants.PASSWORD_EMPTY -> notifyInputInvalid(resources.getString(R.string.password_empty))
            Constants.PASSWORD_SHORT -> notifyInputInvalid(resources.getString(R.string.password_short))
        }
    }

    private fun handleResponse(response: ApiResponse<User>) {
        when (response.status) {
            INVALID -> notifyInputInvalid(response.message)
            SUCCESS -> handlingUserInfo(response.data)
            ERROR -> notifyLoginFail(response.message)
        }
    }

    private fun handlingUserInfo(data: User?) {
        showProgress(false)
        enableLogin(true)
        // Login success
    }

    private fun notifyInputInvalid(msg: String) {
        showProgress(false)
        enableLogin(true)
        Global.showMessage(this, msg)
    }

    private fun notifyLoginFail(error: String) {
        Global.showMessage(this, resources.getString(R.string.login_fail))
        showProgress(false)
        enableLogin(true)
    }

    private fun showProgress(isLoading: Boolean) {
        progressLogin?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun enableLogin(isDisable: Boolean) {
        buttonLogin?.isEnabled = isDisable
    }

    private fun enableRegistration(isDisable: Boolean) {
        buttonRegister?.isEnabled = isDisable
    }
}
