package com.sun.adsfinder01.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sun.adsfinder01.data.model.Account
import com.sun.adsfinder01.data.model.ApiResponse
import com.sun.adsfinder01.data.model.User
import com.sun.adsfinder01.data.repository.UserRepository
import com.sun.adsfinder01.ui.login.EmailAndPasswordValidator.Callback
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _user: MutableLiveData<ApiResponse<User>> by lazy {
        MutableLiveData<ApiResponse<User>>()
    }

    val user: LiveData<ApiResponse<User>>
        get() = _user

    private val _dataValidatorError: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val dataValidatorError: MutableLiveData<String>
        get() = _dataValidatorError

    fun doLogin(email: String?, password: String?) {
        EmailAndPasswordValidator().validate(
            email,
            password,
            object : Callback {

                override fun onEmailEmpty() {
                    dataValidatorError.postValue(EmailAndPasswordValidator.EMAIL_EMPTY)
                }

                override fun onInvalidFormatEmail() {
                    dataValidatorError.postValue(EmailAndPasswordValidator.EMAIL_SYNTAX_ERROR)
                }

                override fun onPasswordEmpty() {
                    dataValidatorError.postValue(EmailAndPasswordValidator.PASSWORD_EMPTY)
                }

                override fun onInvalidLengthPassword() {
                    dataValidatorError.postValue(EmailAndPasswordValidator.PASSWORD_SHORT)
                }

                override fun onValidEmailAndPassword() {
                    doObserve(email, password)
                }
            })
    }

    private fun doObserve(email: String?, password: String?) {
        compositeDisposable.add(
            repository.login(Account(email, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap {
                    return@flatMap Single.just(it.data)
                }
                .subscribe(
                    { user ->
                        _user.postValue(ApiResponse.onSuccess(user))
                    },
                    { error ->
                        _user.postValue(ApiResponse.onError(error.toString()))
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
