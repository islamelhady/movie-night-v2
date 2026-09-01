package com.elhady.movies.core.ui.base

import com.elhady.movies.core.common.AppException

sealed interface ErrorUiState {
    object NoNetwork : ErrorUiState
    object Generic : ErrorUiState
}

fun AppException.toErrorUiState(): ErrorUiState {
    return when (this) {
        is AppException.NoNetwork -> ErrorUiState.NoNetwork
        else -> ErrorUiState.Generic
    }
}
