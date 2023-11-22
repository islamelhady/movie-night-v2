package com.elhady.movies.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.mappers.Mapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE>(initialState: STATE) : ViewModel() {

    protected val _state : MutableStateFlow<STATE> by lazy {
        MutableStateFlow(initialState)
    }
    val state = _state.asStateFlow()
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


}