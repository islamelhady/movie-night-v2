package com.elhady.movies.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.mappers.Mapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE, EVENT>(initialState: STATE) : ViewModel() {

    protected val _state : MutableStateFlow<STATE> by lazy {
        MutableStateFlow(initialState)
    }
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<EVENT>()
    val event = _event.asSharedFlow()
    abstract fun getData()

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

    protected fun sendEvent(event: EVENT){
        viewModelScope.launch {
            _event.emit(event)
        }
    }

}