package com.elhady.movies.core.common

sealed class AppException(message: String? = null) : Exception(message) {
    data object NoNetwork : AppException()
    data object Timeout : AppException()
    data object Unauthorized : AppException()
    data object Forbidden : AppException()
    data object BadRequest : AppException()
    data object Validation : AppException()
    data object NotFound : AppException()
    data object Conflict : AppException()
    data object TooManyRequests : AppException()
    data class Server(val code: Int, val serverMessage: String) : AppException(serverMessage)
    data class Unknown(val errorMessage: String) : AppException(errorMessage)
}
