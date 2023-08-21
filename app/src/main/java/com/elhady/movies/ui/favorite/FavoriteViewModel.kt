package com.elhady.movies.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elhady.movies.ui.base.BaseViewModel

class FavoriteViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Favorite Fragment"
    }
    val text: LiveData<String> = _text
}