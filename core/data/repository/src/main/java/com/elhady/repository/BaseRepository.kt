package com.elhady.repository

import androidx.paging.PagingConfig
import com.elhady.remote.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.net.UnknownHostException
import java.util.Date

abstract class BaseRepository {


    val pagingConfig = PagingConfig(
        pageSize = Constants.ITEMS_PER_PAGE,
        prefetchDistance = 5,
        enablePlaceholders = false
    )

    protected suspend fun <T> wrapApiCall(call: suspend () -> Response<T>): T {
        return try {
            val result = call()
            if (result.code() == UNAUTHORIZED_CODE) {
                throw UnauthorizedThrowable()
            }
            if (result.code() == TIMEOUT_CODE) {
                throw TimeoutThrowable()
            }
            result.body() ?: throw ParsingThrowable()
        } catch (e: UnknownHostException) {
            throw NoNetworkThrowable()
        } catch (e: Exception) {
            throw ApiThrowable(e.message)
        }
    }


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

    protected suspend fun <T, E> wrap(
        request: suspend () -> Response<BaseResponse<T>>,
        mapper: (List<T>?) -> List<E>?,
        insertIntoDatabase: suspend (List<E>) -> Unit
    ) {
        val response = request()
        if (response.isSuccessful) {
            val items = response.body()?.items
            mapper(items)?.let {
                insertIntoDatabase(it)
            }
        } else {
            throw Throwable()
        }
    }

    protected fun <T> wrapWithFlow(function: suspend () -> Response<T>): Flow<State<T>> {
        return flow {
            emit(State.Loading)
            try {
                val response = function()
                if (response.isSuccessful) {
                    emit(State.Success(response.body()))
                } else {
                    emit(State.Error(response.message()))
                }
            } catch (e: java.lang.Exception) {
                emit(State.Error(e.message.toString()))
            }
        }
    }

    protected suspend fun refreshOneTimePerDay(
        requestDate: Long?,
        refreshData: suspend (Date) -> Unit
    ) {
        val currentDate = Date()
        if (requestDate != null) {
            if (Date(requestDate).after(currentDate)) {
                refreshData(currentDate)
            }
        } else {
            refreshData(currentDate)
        }
    }

    private companion object {
        const val UNAUTHORIZED_CODE = 401
        const val TIMEOUT_CODE = 408
    }
}