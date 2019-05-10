package com.sun.adsfinder01.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.sun.adsfinder01.R
import com.sun.adsfinder01.data.model.ApiResponse
import com.sun.adsfinder01.data.model.NetworkStatus.ERROR
import com.sun.adsfinder01.data.model.NetworkStatus.INVALID
import com.sun.adsfinder01.data.model.NetworkStatus.SUCCESS
import com.sun.adsfinder01.data.model.User
import com.sun.adsfinder01.ui.register.RegistrationActivity
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

        viewModel.dataValidatorError.observe(this, Observer { status ->
            handleEmailAndPassword(status)
        })
    }

    private fun initComponent() {
        buttonRegister?.setOnClickListener(this)
        buttonLogin?.setOnClickListener(this)
    }

    private fun handleRegister() {
        Intent(this, RegistrationActivity::class.java).apply {
            startActivity(this)
        }
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
            EmailAndPasswordValidator.EMAIL_EMPTY -> notifyInputInvalid(resources.getString(R.string.email_empty))
            EmailAndPasswordValidator.EMAIL_SYNTAX_ERROR -> notifyInputInvalid(resources.getString(R.string.email_syntax_error))
            EmailAndPasswordValidator.PASSWORD_EMPTY -> notifyInputInvalid(resources.getString(R.string.password_empty))
            EmailAndPasswordValidator.PASSWORD_SHORT -> notifyInputInvalid(resources.getString(R.string.password_short))
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
        showMessage(msg)
    }

    private fun notifyLoginFail(error: String) {
        showMessage(resources.getString(R.string.login_fail))
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

    private fun Context.showMessage(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
