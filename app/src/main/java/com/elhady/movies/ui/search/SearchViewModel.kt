package com.elhady.movies.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elhady.movies.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Search Fragment"
    }
    val text: LiveData<String> = _text
}