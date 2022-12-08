package com.gvelesiani.socialx.common

import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityCompat

fun hideKeyboard(v: View?, removeFocus: Boolean = true) {
    if (v != null) {
        if (!v.hasFocus() && removeFocus) {
            v.isFocusable = false
        }
        val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }
}

fun hasPermissions(context: Context, vararg permissions: String): Boolean =
    permissions.all {
        ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }