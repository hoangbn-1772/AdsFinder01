package com.sun.adsfinder01.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sun.adsfinder01.data.model.Account
import com.sun.adsfinder01.data.model.ApiResponse
import com.sun.adsfinder01.data.model.User
import com.sun.adsfinder01.data.repository.UserRepository
import com.sun.adsfinder01.ui.login.EmailAndPasswordValidator.Callback
import com.sun.adsfinder01.util.Constants
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

    private val _emailOrPassword: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val emailOrPassword: MutableLiveData<String>
        get() = _emailOrPassword

    fun doLogin(email: String?, password: String?) {
        EmailAndPasswordValidator().validateEmail(email, password, object : Callback {
            override fun onEmailEmpty() {
                _emailOrPassword.postValue(Constants.EMAIL_EMPTY)
            }

            override fun onInvalidFormatEmail() {
                _emailOrPassword.postValue(Constants.EMAIL_SYNTAX_ERROR)
            }

            override fun onPasswordEmpty() {
                _emailOrPassword.postValue(Constants.PASSWORD_EMPTY)
            }

            override fun onInvalidLengthPassword() {
                _emailOrPassword.postValue(Constants.PASSWORD_SHORT)
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

    fun onDestroy() {
        compositeDisposable.clear()
    }
}
