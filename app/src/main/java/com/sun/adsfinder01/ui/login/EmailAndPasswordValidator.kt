package com.sun.adsfinder01.ui.login

import androidx.annotation.NonNull
import androidx.core.util.PatternsCompat
import com.sun.adsfinder01.util.Constants

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

    interface Callback {
        fun onEmailEmpty()

        fun onInvalidFormatEmail()

        fun onPasswordEmpty()

        fun onInvalidLengthPassword()

        fun onValidEmailAndPassword()
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 4

        const val VALID = "VALID"

        const val EMAIL_EMPTY = "EMAIL_EMPTY"

        const val PASSWORD_EMPTY = "PASSWORD_EMPTY"

        const val EMAIL_SYNTAX_ERROR = "EMAIL_SYNTAX_ERROR"

        const val PASSWORD_SHORT = "PASSWORD_SHORT"
    }
}
