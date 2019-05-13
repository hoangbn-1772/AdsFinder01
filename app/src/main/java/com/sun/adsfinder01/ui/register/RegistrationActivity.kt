package com.sun.adsfinder01.ui.register

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.CompoundButton
import android.widget.CompoundButton.OnCheckedChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.sun.adsfinder01.R
import com.sun.adsfinder01.data.model.ApiResponse
import com.sun.adsfinder01.data.model.NetworkStatus.ERROR
import com.sun.adsfinder01.data.model.NetworkStatus.INVALID
import com.sun.adsfinder01.data.model.NetworkStatus.SUCCESS
import com.sun.adsfinder01.data.model.User
import com.sun.adsfinder01.ui.login.EmailAndPasswordValidator
import com.sun.adsfinder01.util.ContextExtension.showMessage
import kotlinx.android.synthetic.main.activity_registration.buttonRegisterAccount
import kotlinx.android.synthetic.main.activity_registration.checkboxPassword
import kotlinx.android.synthetic.main.activity_registration.editConfirmPassword
import kotlinx.android.synthetic.main.activity_registration.editEmailRegistration
import kotlinx.android.synthetic.main.activity_registration.editPasswordRegistration
import kotlinx.android.synthetic.main.activity_registration.progressLoading
import kotlinx.android.synthetic.main.activity_registration.toolbarRegistrationScreen
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationActivity : AppCompatActivity(), View.OnClickListener, OnCheckedChangeListener {

    private val viewModel: RegistrationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        initComponents()

        doObserve()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonRegisterAccount -> registerAccount()
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when {
            isChecked -> hidePassword()
            else -> showPassword()
        }
    }

    private fun initComponents() {
        initToolbar()
        buttonRegisterAccount?.setOnClickListener(this)
        checkboxPassword?.setOnCheckedChangeListener(this)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbarRegistrationScreen)
        supportActionBar?.title = resources.getString(R.string.register)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbarRegistrationScreen.setNavigationOnClickListener {
            finish()
        }
    }

    private fun doObserve() {
        viewModel.userLiveData.observe(this, Observer { response ->
            handleResponse(response)
            doLoading(false)
            enableRegister(true)
        })

        viewModel.dataValidatorError.observe(this, Observer { status ->
            showError(status)
            doLoading(false)
            enableRegister(true)
        })
    }

    private fun registerAccount() {
        doLoading(true)
        enableRegister(false)

        val emailInput = editEmailRegistration.text.toString()
        val passwordInput = editPasswordRegistration.text.toString()
        val confirmPassword = editConfirmPassword.text.toString()

        viewModel.handleRegister(emailInput, passwordInput, confirmPassword)
    }

    private fun showError(errorType: String) {
        when (errorType) {
            EmailAndPasswordValidator.EMAIL_EMPTY -> showMessage(resources.getString(R.string.email_empty))
            EmailAndPasswordValidator.EMAIL_SYNTAX_ERROR -> showMessage(resources.getString(R.string.email_syntax_error))
            EmailAndPasswordValidator.PASSWORD_EMPTY -> showMessage(resources.getString(R.string.password_empty))
            EmailAndPasswordValidator.PASSWORD_SHORT -> showMessage(resources.getString(R.string.password_short))
            EmailAndPasswordValidator.CONFIRM_PASSWORD_FAILURE -> showMessage(resources.getString(R.string.confirm_password_failure))
        }
    }

    private fun showPassword() {
        checkboxPassword?.text = resources.getString(R.string.show_password)
        editPasswordRegistration?.transformationMethod = PasswordTransformationMethod.getInstance()
        editConfirmPassword?.transformationMethod = PasswordTransformationMethod.getInstance()
        editPasswordRegistration?.text?.length?.let { editPasswordRegistration?.setSelection(it) }
        editConfirmPassword?.text?.length?.let { editConfirmPassword?.setSelection(it) }
    }

    private fun hidePassword() {
        checkboxPassword?.text = resources.getString(R.string.hide_password)
        editPasswordRegistration?.transformationMethod = HideReturnsTransformationMethod.getInstance()
        editConfirmPassword?.transformationMethod = HideReturnsTransformationMethod.getInstance()
        editPasswordRegistration?.text?.length?.let { editPasswordRegistration?.setSelection(it) }
        editConfirmPassword?.text?.length?.let { editConfirmPassword?.setSelection(it) }
    }

    private fun handleResponse(response: ApiResponse<User>) {
        when (response.status) {
            INVALID -> showMessage(response.message)
            SUCCESS -> registerSuccess(response.data)
            ERROR -> showMessage(resources.getString(R.string.register_fail))
        }
    }

    private fun registerSuccess(data: User?) {
        // Register success
    }

    private fun enableRegister(isEnable: Boolean) {
        buttonRegisterAccount?.isEnabled = isEnable
    }

    private fun doLoading(isLoading: Boolean) {
        progressLoading?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
