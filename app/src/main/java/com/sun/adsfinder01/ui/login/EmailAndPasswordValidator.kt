package com.sun.adsfinder01.ui.login

import androidx.annotation.NonNull
import androidx.core.util.PatternsCompat
import com.sun.adsfinder01.util.Constants

class EmailAndPasswordValidator {
    fun validateEmail(email: String?, password: String?, @NonNull callback: Callback) {
        if (email.isNullOrEmpty()) {
            callback.onEmailEmpty()
            return
        }
        if (!PatternsCompat.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            callback.onInvalidFormatEmail()
            return
        }

        if (password.isNullOrEmpty()) {
            callback.onPasswordEmpty()
            return
        }

        if (password.trim().length < Constants.MIN_PASSWORD_LENGTH) {
            callback.onInvalidLengthPassword()
            return
        }

        callback.onValidEmailAndPassword()
    }

    interface Callback {
        fun onEmailEmpty()

        fun onInvalidFormatEmail()

        fun onPasswordEmpty()

        fun onInvalidLengthPassword()

        fun onValidEmailAndPassword()
    }
}
