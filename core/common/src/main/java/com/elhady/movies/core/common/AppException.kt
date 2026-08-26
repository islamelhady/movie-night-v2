package com.elhady.movies.core.common

sealed class AppException(message: String? = null) : Exception(message) {
     object NoNetwork : AppException()
     object Timeout : AppException()
     object Unauthorized : AppException()
     object Forbidden : AppException()
     object BadRequest : AppException()
     object Validation : AppException()
     object NotFound : AppException()
     object Conflict : AppException()
     object TooManyRequests : AppException()
     object EmptyResponse : AppException()
     data class Server(val code: Int, val serverMessage: String) : AppException(serverMessage)
    data class Unknown(val errorMessage: String) : AppException(errorMessage)
}
