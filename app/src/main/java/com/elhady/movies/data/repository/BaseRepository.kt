package com.elhady.movies.data.repository

import com.elhady.movies.data.remote.State
import com.elhady.movies.data.remote.response.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

abstract class BaseRepository {
    protected fun <I, O> wrap(
        function: suspend () -> Response<I>,
        mapper: (I) -> O
    ): Flow<State<O>> {
        return flow {
            emit(State.Loading)
            try {
                val response = function()
                if (response.isSuccessful) {
                    val items = response.body()?.let { mapper(it) }
                    emit(State.Success(items))
                } else {
                    emit(State.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(State.Error(e.message.toString()))
            }
        }
    }

    protected suspend fun <T> wrapWithService(
        request: suspend () -> Response<BaseResponse<T>>,
    ): Flow<List<T>> {
        return flow {
            val response = request()
            if (response.isSuccessful) {
                response.body()?.items?.let {
                    emit(it)
                }
            }
        }
    }




}