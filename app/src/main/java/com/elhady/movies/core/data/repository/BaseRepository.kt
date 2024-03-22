package com.elhady.movies.core.data.repository

import com.elhady.movies.core.data.remote.response.DataWrapperResponse
import com.elhady.movies.core.domain.usecase.repository.ApiThrowable
import com.elhady.movies.core.domain.usecase.repository.NoNetworkThrowable
import com.elhady.movies.core.domain.usecase.repository.ParsingThrowable
import com.elhady.movies.core.domain.usecase.repository.ServerErrorThrowable
import com.elhady.movies.core.domain.usecase.repository.TimeoutThrowable
import com.elhady.movies.core.domain.usecase.repository.UnauthorizedThrowable
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseRepository {

//    protected suspend fun <T> wrapApiCall(call: suspend () -> Response<T>): T {
//        return try {
//            val result = call()
//            if (result.code() == UNAUTHORIZED_CODE) {
//                throw UnauthorizedThrowable()
//            }
//            if (result.code() == TIMEOUT_CODE) {
//                throw TimeoutThrowable()
//            }
//            result.body() ?: throw ParsingThrowable()
//        } catch (e: UnknownHostException) {
//            throw NoNetworkThrowable()
//        } catch (e: Exception) {
//            throw ApiThrowable(e.message)
//        }
//    }

    protected suspend fun <T> wrapApiCall(call: suspend () -> Response<T>): T {
        return try {
            val result = call()
            when (result.code()) {
                in 200..299 -> result.body() ?: throw ParsingThrowable()
                401 -> throw UnauthorizedThrowable()
                408 -> throw TimeoutThrowable()
                500 -> throw ServerErrorThrowable()
                // Add more status code checks as needed
                else -> throw ApiThrowable("Unhandled status code: ${result.code()}")
            }
        } catch (e: UnknownHostException) {
            throw NoNetworkThrowable()
        } catch (e: SocketTimeoutException) {
            throw TimeoutThrowable()
        } catch (e: Exception) {
            throw ApiThrowable(e.message)
        }
    }



    protected suspend fun <INPUT, OUTPUT> refreshWrapper(
        apiCall: suspend () -> Response<DataWrapperResponse<INPUT>>,
        localMapper: (INPUT) -> OUTPUT,
        databaseSaver: suspend (List<OUTPUT>) -> Unit,
        clearOldLocalData: (suspend () -> Unit)? = null,
    ) {
        try {
            wrapApiCall(apiCall).results
                ?.filterNotNull()
                ?.let {
                    clearOldLocalData?.invoke()
                    databaseSaver(it.map { item -> localMapper(item) })
                }
        } catch (_: Throwable) {
        }
    }

    private companion object {
        const val UNAUTHORIZED_CODE = 401

        const val OK = 200 // The request has succeeded.
        const val CREATED = 201 // A new resource has been created as a result of the request.
        const val NO_CONTENT = 204 // The server successfully processed the request, but is not returning any content.
        const val MOVED_PERMANENTLY = 301 // The resource has been moved to a new URL permanently.
        const val BAD_REQUEST = 400 // The server cannot process the request due to a client error.
        const val FORBIDDEN = 403 // The server understood the request but refuses to authorize it.
        const val NOT_FOUND = 404 // The server can’t find the requested resource.
        const val INTERNAL_SERVER_ERROR = 500 // The server encountered an unexpected condition that prevented it from fulfilling the request.
        const val SERVICE_UNAVAILABLE = 503 // The server is not ready to handle the request, often due to maintenance or overload.
        const val TIMEOUT_CODE = 408 // indicates that the server timed out waiting for the client’s request.
    }
}