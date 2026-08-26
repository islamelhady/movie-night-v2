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

    val animationRes: Int
        get() = when (this) {
            is NoNetwork -> R.raw.no_connection
            is Timeout -> R.raw.timeout
            is Unauthorized -> R.raw.no_item_have
            is Forbidden -> R.raw.no_item_have
            is BadRequest -> R.raw.no_item_have
            is Validation -> R.raw.no_item_have
            is Conflict -> R.raw.no_item_have
            is NotFound -> R.raw.no_item_have
            is Server -> R.raw.no_item_have
            is TooManyRequests -> R.raw.no_item_have
            is Unknown -> R.raw.no_item_have
            is EmptyResponse -> R.raw.no_viedo
        }

    val messageRes: Int
        get() = when (this) {
            is NoNetwork -> R.string.no_network_connection
            is Timeout -> R.string.time_out
            is Unauthorized -> R.string.unauthorized_error
            is Forbidden -> R.string.forbidden_error
            is BadRequest -> R.string.bad_request_error
            is Validation -> R.string.validation_error
            is Conflict -> R.string.conflict_error
            is NotFound -> R.string.not_found_error
            is EmptyResponse -> R.string.empty_response_error
            is TooManyRequests -> R.string.too_many_requests_error
            is Server -> R.string.server_error
            is Unknown -> R.string.unknown_error
        }
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
