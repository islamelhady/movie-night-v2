package com.elhady.movies.core.network.exception

import com.elhady.movies.core.common.AppException
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.HttpRetryException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ExceptionMapper : AppExceptionMapper {

    override fun map(exception: Exception): AppException {
        return when (exception) {
            is SocketTimeoutException -> AppException.Timeout
            is HttpRetryException -> AppException.Server(
                code = exception.responseCode(),
                serverMessage = exception.message ?: "Server Error"
            )
            is UnknownHostException,
            is ConnectException -> AppException.NoNetwork

            is IOException -> AppException.Unknown(errorMessage = exception.message ?: "IO Error")

            is HttpException -> map(code = exception.code(), message = exception.message())
            else -> AppException.Unknown(errorMessage = exception.message ?: "Unknown error")
        }
    }

    override fun map(code: Int, message: String?): AppException {
        return when (code) {
            400 -> AppException.BadRequest
            401 -> AppException.Unauthorized
            403 -> AppException.Forbidden
            404 -> AppException.NotFound
            409 -> AppException.Conflict
            422 -> AppException.Validation
            429 -> AppException.TooManyRequests
            in 500..599 -> AppException.Server(
                code = code,
                serverMessage = message ?: "Server Error"
            )

            else -> AppException.Unknown(errorMessage = message ?: "Unknown HTTP Error")
        }
    }
}