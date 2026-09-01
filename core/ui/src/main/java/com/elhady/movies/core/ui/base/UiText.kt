package com.elhady.movies.core.ui.base

import android.content.Context
import androidx.annotation.StringRes

sealed interface UiText {
    data class Dynamic(val value: String) : UiText
    class Resource(
        @StringRes val resId: Int,
        val args: List<Any> = emptyList()
    ) : UiText

    fun asString(context: Context): String {
        return when (this) {
            is Dynamic -> value
            is Resource -> context.getString(resId, *args.toTypedArray())
        }
    }
}
