package com.sun.adsfinder01.util

import android.content.Context
import android.widget.Toast

object Global {

    fun showMessage(context: Context, msg: String?) = Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}
