package com.elhady.movies.ui.search

import androidx.lifecycle.ViewModel
import com.elhady.movies.data.remote.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
}