package com.elhady.movies.core.bases

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.elhady.movies.core.mapper.Mapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE, EVENT>(initialState: STATE) : ViewModel() {

    protected val _state: MutableStateFlow<STATE> by lazy { MutableStateFlow(initialState) }
    val state = _state.asStateFlow()

    protected val _event = MutableSharedFlow<EVENT>()
    val event = _event.asSharedFlow()

    protected fun <T> tryToExecute(
        call: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            try {
                call().also(onSuccess)
            } catch (th: Throwable) {
                onError(th)
            }
        }
    }

    protected fun <INPUT, OUTPUT> tryToExecute(
        call: suspend () -> List<INPUT>,
        onSuccess: (List<OUTPUT>) -> Unit,
        mapper: Mapper<INPUT, OUTPUT>,
        onError: (Throwable) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            try {
                mapper.map(call()).also(onSuccess)
            } catch (th: Throwable) {
                onError(th)
            }
        }
    }


    protected fun <INPUT, OUTPUT> tryToExecute(
        call: suspend () -> INPUT,
        mapper: Mapper<INPUT, OUTPUT>,
        onSuccess: (OUTPUT) -> Unit,
        onError: (Throwable) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            try {
                mapper.map(call()).also(onSuccess)
            } catch (th: Throwable) {
                onError(th)
            }
        }
    }

    protected fun <INPUT : Any, OUTPUT : Any> wrapperPager(
        data: suspend () -> Flow<PagingData<INPUT>>,
        mapper: Mapper<INPUT,OUTPUT>,
        onSuccess: (Flow<PagingData<OUTPUT>>) -> Unit,
        onError: (Throwable) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            try {
                data().map { pagingData ->
                    pagingData.map (mapper::map)
                }.cachedIn(viewModelScope).also(onSuccess)
            } catch (th: Throwable) {
                onError(th)
            }
        }
    }

    protected fun sendEvent(event: EVENT) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

}