package com.elhady.movies.core.ui.base

import com.elhady.movies.core.common.NoNetworkThrowable
import com.elhady.movies.core.common.ServerErrorThrowable
import com.elhady.movies.core.common.UnauthorizedThrowable

sealed class UiError {
    object NoConnection : UiError()
    object Server : UiError()
    data class Unknown(val message: String?) : UiError()
    object Unauthorized : UiError()
}

fun Throwable.toUiError(): UiError {
    return when (this) {
        is NoNetworkThrowable -> UiError.NoConnection
        is ServerErrorThrowable -> UiError.Server
        is UnauthorizedThrowable -> UiError.Unauthorized
        else -> UiError.Unknown(this.message)
    }
}
