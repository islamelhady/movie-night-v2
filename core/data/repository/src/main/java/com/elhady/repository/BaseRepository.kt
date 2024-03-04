package com.elhady.repository

import androidx.paging.PagingConfig
import com.elhady.remote.response.DataWrapperResponse
import com.elhady.usecase.repository.ApiThrowable
import com.elhady.usecase.repository.NoNetworkThrowable
import com.elhady.usecase.repository.ParsingThrowable
import com.elhady.usecase.repository.TimeoutThrowable
import com.elhady.usecase.repository.UnauthorizedThrowable
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


    protected suspend fun <INPUT, OUTPUT> refreshWrapper(
        apiCall: suspend () -> Response<DataWrapperResponse<INPUT>>,
        localMapper: (INPUT)-> OUTPUT,
        clearOldLocalData: (suspend () -> Unit)? = null,
        insertIntoDatabase: suspend (List<OUTPUT>) -> Unit
    ) {
        try {
            wrapApiCall(apiCall).results?.filterNotNull()?.let {
                clearOldLocalData?.invoke()
                insertIntoDatabase(it.map { item ->
                    localMapper(item)
                })
            }
        } catch (th: Throwable){
            th.message
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

//    protected suspend fun <T> wrapWithService(
//        request: suspend () -> Response<DataWrapperResponse<T>>,
//    ): Flow<List<T>> {
//        return flow {
//            val response = request()
//            if (response.isSuccessful) {
//                response.body()?.result?.let {
//                    emit(it)
//                }
//            }
//        }
//    }

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