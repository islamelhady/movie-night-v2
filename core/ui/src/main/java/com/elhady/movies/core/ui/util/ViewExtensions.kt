package com.elhady.movies.core.ui.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Hides the software keyboard.
 */
fun View.hideKeyboard() {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}
