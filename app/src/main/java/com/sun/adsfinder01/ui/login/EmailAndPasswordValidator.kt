package com.sun.adsfinder01.ui.login

import androidx.annotation.NonNull
import androidx.core.util.PatternsCompat

class EmailAndPasswordValidator {

    fun validate(email: String?, password: String?, @NonNull callback: Callback) {
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

        if (password.trim().length < MIN_PASSWORD_LENGTH) {
            callback.onInvalidLengthPassword()
            return
        }

        callback.onValidEmailAndPassword()
    }

    fun validate(
        email: String?,
        password: String?,
        confirmPassword: String?, @NonNull callback: RegistrationCallback
    ) {
        validate(email, password, callback)

        if (password?.trim() != confirmPassword?.trim()) {
            callback.onInvalidConfirmPassword()
            return
        }

        callback.onConfirmSuccess()
    }

    interface Callback {
        fun onEmailEmpty()

        fun onInvalidFormatEmail()

        fun onPasswordEmpty()

        fun onInvalidLengthPassword()

        fun onValidEmailAndPassword()
    }

    interface RegistrationCallback : Callback {
        fun onInvalidConfirmPassword()

        fun onConfirmSuccess()
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 6

        const val VALID = "VALID"

        const val EMAIL_EMPTY = "EMAIL_EMPTY"

        const val PASSWORD_EMPTY = "PASSWORD_EMPTY"

        const val EMAIL_SYNTAX_ERROR = "EMAIL_SYNTAX_ERROR"

        const val PASSWORD_SHORT = "PASSWORD_SHORT"

        const val CONFIRM_PASSWORD_FAILURE = "CONFIRM_PASSWORD_FAILURE"

        const val PASSWORD_DEFAULT = "PASSWORD_DEFAULT"
    }
}
