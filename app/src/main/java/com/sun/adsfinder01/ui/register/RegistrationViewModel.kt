package com.sun.adsfinder01.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sun.adsfinder01.data.model.Account
import com.sun.adsfinder01.data.model.ApiResponse
import com.sun.adsfinder01.data.model.User
import com.sun.adsfinder01.data.repository.UserRepository
import com.sun.adsfinder01.ui.login.EmailAndPasswordValidator
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RegistrationViewModel(private val repository: UserRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _userLiveData: MutableLiveData<ApiResponse<User>> by lazy {
        MutableLiveData<ApiResponse<User>>()
    }

    private val _dataValidatorError: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val userLiveData: LiveData<ApiResponse<User>>
        get() = _userLiveData

    val dataValidatorError: LiveData<String>
        get() = _dataValidatorError

    fun handleRegister(email: String?, password: String?, confirmPassword: String?) {
        EmailAndPasswordValidator().validate(email, password, confirmPassword,
            object : EmailAndPasswordValidator.RegistrationCallback {

                override fun onEmailEmpty() {
                    _dataValidatorError.postValue(EmailAndPasswordValidator.EMAIL_EMPTY)
                }

                override fun onInvalidFormatEmail() {
                    _dataValidatorError.postValue(EmailAndPasswordValidator.EMAIL_SYNTAX_ERROR)
                }

                override fun onPasswordEmpty() {
                    _dataValidatorError.postValue(EmailAndPasswordValidator.PASSWORD_EMPTY)
                }

                override fun onInvalidLengthPassword() {
                    _dataValidatorError.postValue(EmailAndPasswordValidator.PASSWORD_SHORT)
                }

                override fun onInvalidConfirmPassword() {
                    _dataValidatorError.postValue(EmailAndPasswordValidator.CONFIRM_PASSWORD_FAILURE)
                }

                override fun onValidEmailAndPassword() {
                }

                override fun onConfirmSuccess() {
                    doObserve(email, password)
                }
            })
    }

    private fun doObserve(email: String?, password: String?) {
        compositeDisposable.add(
            repository.register(Account(email, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap {
                    return@flatMap Single.just(it.data)
                }
                .subscribe(
                    { user ->
                        _userLiveData.postValue(ApiResponse.onSuccess(user))
                    },
                    { error ->
                        _userLiveData.postValue(ApiResponse.onError(error.toString()))
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
