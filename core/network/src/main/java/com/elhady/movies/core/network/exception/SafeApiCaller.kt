package com.elhady.movies.core.network.exception

import com.elhady.movies.core.common.AppException
import kotlinx.coroutines.CancellationException
import retrofit2.Response
import javax.inject.Inject

class SafeApiCaller @Inject constructor(private val exceptionMapper: AppExceptionMapper) {

    suspend fun <T> execute(call: suspend () -> Response<T>): T {
        return try {
            val response = call()
            if (response.isSuccessful) {
                response.body() ?: throw AppException.EmptyResponse
            } else {
                throw exceptionMapper.map(code = response.code(), message = response.message())
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: AppException){
           throw e
        } catch (e: Exception) {
            throw exceptionMapper.map(input = e)
        }
    }
}