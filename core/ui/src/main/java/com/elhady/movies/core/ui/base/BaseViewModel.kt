package com.elhady.movies.core.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.elhady.movies.core.common.AppException
import com.elhady.movies.core.common.mapper.Mapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * A base class for all ViewModels in the project.
 *
 * @param STATE The type of the UI state.
 * @param EFFECT The type of the UI effect.
 * @param initialState The initial state of the ViewModel.
 */
abstract class BaseViewModel<STATE, EFFECT>(initialState: STATE) : ViewModel() {

    protected val _state: MutableStateFlow<STATE> by lazy { MutableStateFlow(initialState) }
    val state = _state.asStateFlow()

    protected val _effect = MutableSharedFlow<EFFECT>()
    val effect = _effect.asSharedFlow()

    protected fun sendEffect(effect: EFFECT) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    /**
     * Executes a suspending call and handles the result.
     *
     * @param T The type of the result.
     * @param call The suspending function to execute.
     * @param onSuccess The callback to execute on success.
     * @param onError The callback to execute on error.
     * @param dispatcher The dispatcher on which to execute the call.
     */
    protected fun <T> tryToExecute(
        call: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (AppException) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): Job {
        return viewModelScope.launch(dispatcher) {
            try {
                call().also(onSuccess)
            } catch (exception: AppException) {
                onError(exception)
            }
        }
    }


    @JvmName("tryToExecuteList")
    protected fun <INPUT, OUTPUT> tryToExecute(
        call: suspend () -> List<INPUT>,
        mapper: Mapper<INPUT, OUTPUT>,
        onSuccess: (List<OUTPUT>) -> Unit,
        onError: (AppException) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): Job {
        return viewModelScope.launch(dispatcher) {
            try {
                mapper.map(call()).also(onSuccess)
            } catch (exception: AppException) {
                onError(exception)
            }
        }
    }

    @JvmName("tryToExecuteSingle")
    protected fun <INPUT, OUTPUT> tryToExecute(
        call: suspend () -> INPUT,
        mapper: Mapper<INPUT, OUTPUT>,
        onSuccess: (OUTPUT) -> Unit,
        onError: (AppException) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): Job {
        return viewModelScope.launch(dispatcher) {
            try {
                mapper.map(call()).also(onSuccess)
            } catch (exception: AppException) {
                onError(exception)
            }
        }
    }

    protected suspend fun <INPUT, OUTPUT> tryToExecuteAsync(
        call: suspend () -> List<INPUT>,
        mapper: Mapper<INPUT, OUTPUT>,
        onSuccess: (List<OUTPUT>) -> Unit,
        onError: (AppException) -> Unit,
    ) {
        try {
            call()
                .map(mapper::map)
                .also(onSuccess)
        } catch (exception: AppException) {
            onError(exception)
        }
    }


    protected fun <INPUT : Any, OUTPUT : Any> wrapperPager(
        data: suspend () -> Flow<PagingData<INPUT>>,
        mapper: Mapper<INPUT, OUTPUT>,
        onSuccess: (Flow<PagingData<OUTPUT>>) -> Unit,
        onError: (AppException) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            try {
                data().map { pagingData ->
                    pagingData.map(mapper::map)
                }.cachedIn(viewModelScope).also(onSuccess)
            } catch (exception: AppException) {
                onError(exception)
            }
        }
    }

}
