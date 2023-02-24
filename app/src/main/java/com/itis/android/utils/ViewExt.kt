package com.itis.android.utils

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(
    @StringRes message: Int,
    duration: Int = Snackbar.LENGTH_SHORT
) = Snackbar
    .make(this, message, duration)
    .show()
