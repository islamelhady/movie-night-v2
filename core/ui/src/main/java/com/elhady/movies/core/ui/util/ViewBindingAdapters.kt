package com.elhady.movies.core.ui.util

import android.app.UiModeManager
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.BindingAdapter
import com.elhady.movies.core.ui.R
import com.elhady.movies.core.ui.base.ErrorUiState
import com.google.android.material.progressindicator.LinearProgressIndicator

@BindingAdapter(value = ["app:genres"])
fun setGenres(textView: TextView, genres: List<String>?){
    genres?.let {
        textView.text = genres.joinToString(" • ") { it }
    }
}

@BindingAdapter(value = ["app:isVisible"])
fun View.isVisible(isVisible: Boolean) {
    if (isVisible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.INVISIBLE
    }
}

@BindingAdapter(value = ["app:isVisibleOrGone"])
fun View.isVisibleOrGone(isVisible: Boolean?) {
    if (isVisible == true) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

@BindingAdapter(value = ["app:hideWhenNotLoggedIn"])
fun View.hideWhenNotLoggedIn(hideWhenNotLoggedIn: Boolean?) {
    if (hideWhenNotLoggedIn == false) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.INVISIBLE
    }
}

@BindingAdapter("app:setTipError")
fun EditText.setTipError(errorMessage: String?) {
    if (errorMessage == null) return
    else error = errorMessage
}

@BindingAdapter(value = ["app:hideWhenNoList"])
fun <T> View.hideWhenNoList(list: List<T>?) {
    if (list.isNullOrEmpty()) {
        this.visibility = View.GONE
    } else {
        this.visibility = View.VISIBLE
    }
}

@BindingAdapter(value = ["app:hideWhenNoResult"])
fun <T> View.hideWhenNoResult(list: List<T>?) {
    if (list.isNullOrEmpty()) {
        this.visibility = View.GONE
    } else {
        this.visibility = View.VISIBLE
    }
}

@BindingAdapter(value = ["app:hideWhenEmpty"])
fun <T> View.hideWhenEmpty(list: List<T>?) {
    if (list.isNullOrEmpty()) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

@BindingAdapter(value = ["app:loading"])
fun LinearProgressIndicator.isLoading(isLoading: Boolean?) {
    if (isLoading == true) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

@BindingAdapter(value = ["app:showWhenNoResult"])
fun <T> View.showWhenNoResult(list: List<T>?) {
    if (list.isNullOrEmpty()) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

@BindingAdapter("app:showWhenError")
fun <T> View.showWhenError(list: List<T>?) {
    if (list?.isEmpty() == true) {
        this.visibility = View.GONE
    } else {
        this.visibility = View.VISIBLE
    }
}

@BindingAdapter("app:toggleUiMode")
fun SwitchCompat.toggleUiMode(uiModeManager: UiModeManager) {
    this.setOnCheckedChangeListener { _, isChecked ->
        if (isChecked) {
            uiModeManager.nightMode = UiModeManager.MODE_NIGHT_NO
        } else {
            uiModeManager.nightMode = UiModeManager.MODE_NIGHT_YES
        }
    }
}

@BindingAdapter(value = ["app:showWhenErrorUi"])
fun View.showWhenErrorUi(uiError: ErrorUiState?) {
    if (uiError != null) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

@BindingAdapter("app:hideWhenError")
fun <T> View.hideWhenError(list: List<T>?) {
    if (list?.isEmpty() == true) {
        this.visibility = View.GONE
    } else {
        this.visibility = View.VISIBLE
    }
}

@BindingAdapter("app:hideWhenRefresh")
fun <T> View.hideWhenRefresh(isRefresh: Boolean?) = if (isRefresh == true) {
    this.visibility = View.GONE
} else {
    this.visibility = View.VISIBLE
}

@BindingAdapter("app:hideWhenLoading")
fun <T> View.hideWhenLoading(isLoading: Boolean?) {
    if (isLoading == true) {
        this.visibility = View.GONE
    } else {
        this.visibility = View.VISIBLE
    }
}

@BindingAdapter("app:onClickNavigation")
fun androidx.appcompat.widget.Toolbar.addNavigationListener(onClick: () -> Unit) {
    this.setNavigationOnClickListener {
        onClick()
    }
}

@BindingAdapter("convertGenderText")
fun TextView.convertGenderText(gender: String?) {
    text = when (gender) {
        "1" -> context.getString(R.string.female)
        "2" -> context.getString(R.string.male)
        else -> ""
    }.takeIf { gender != null } ?: ""
}
