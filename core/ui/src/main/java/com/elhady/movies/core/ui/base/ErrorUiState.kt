package com.elhady.movies.core.ui.base

import com.elhady.movies.core.common.AppException
import com.elhady.movies.core.ui.R

sealed class ErrorUiState {
    object NoNetwork : ErrorUiState()
    object Timeout : ErrorUiState()
    object Unauthorized : ErrorUiState()
    object Forbidden : ErrorUiState()
    object BadRequest : ErrorUiState()
    object Validation : ErrorUiState()
    object NotFound : ErrorUiState()
    object Conflict : ErrorUiState()
    object TooManyRequests : ErrorUiState()
    object EmptyResponse : ErrorUiState()
    data class Server(val code: Int, val serverMessage: String?) : ErrorUiState()
    data class Unknown(val errorMessage: String?) : ErrorUiState()
}

fun AppException.toErrorUiState(): ErrorUiState {
    return when (this) {
        is AppException.NoNetwork -> ErrorUiState.NoNetwork
        is AppException.Unauthorized -> ErrorUiState.Unauthorized
        is AppException.Forbidden -> ErrorUiState.Forbidden
        is AppException.BadRequest -> ErrorUiState.BadRequest
        is AppException.Validation -> ErrorUiState.Validation
        is AppException.NotFound -> ErrorUiState.NotFound
        is AppException.Conflict -> ErrorUiState.Conflict
        is AppException.TooManyRequests -> ErrorUiState.TooManyRequests
        is AppException.Timeout -> ErrorUiState.Timeout
        is AppException.EmptyResponse -> ErrorUiState.EmptyResponse
        is AppException.Server -> ErrorUiState.Server(code = this.code, serverMessage = this.serverMessage)
        is AppException.Unknown -> ErrorUiState.Unknown(errorMessage = this.errorMessage)
    }
}

val ErrorUiState.animationRes: Int
    get() = when (this) {
        is ErrorUiState.NoNetwork -> R.raw.no_connection
        is ErrorUiState.Timeout -> R.raw.timeout
        is ErrorUiState.Unauthorized -> R.raw.no_item_have
        is ErrorUiState.Forbidden -> R.raw.no_item_have
        is ErrorUiState.BadRequest -> R.raw.no_item_have
        is ErrorUiState.Validation -> R.raw.no_item_have
        is ErrorUiState.Conflict -> R.raw.no_item_have
        is ErrorUiState.NotFound -> R.raw.no_item_have
        is ErrorUiState.Server -> R.raw.no_item_have
        is ErrorUiState.TooManyRequests -> R.raw.no_item_have
        is ErrorUiState.Unknown -> R.raw.no_item_have
        is ErrorUiState.EmptyResponse -> R.raw.no_viedo
    }

val ErrorUiState.messageRes: Int
    get() = when (this) {
        is ErrorUiState.NoNetwork -> R.string.no_network_connection
        is ErrorUiState.Timeout -> R.string.time_out
        is ErrorUiState.Unauthorized -> R.string.unauthorized_error
        is ErrorUiState.Forbidden -> R.string.forbidden_error
        is ErrorUiState.BadRequest -> R.string.bad_request_error
        is ErrorUiState.Validation -> R.string.validation_error
        is ErrorUiState.Conflict -> R.string.conflict_error
        is ErrorUiState.NotFound -> R.string.not_found_error
        is ErrorUiState.EmptyResponse -> R.string.empty_response_error
        is ErrorUiState.TooManyRequests -> R.string.too_many_requests_error
        is ErrorUiState.Server -> R.string.server_error
        is ErrorUiState.Unknown -> R.string.unknown_error
    }
