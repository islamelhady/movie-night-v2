package com.elhady.movies.ui.search

import androidx.lifecycle.ViewModel
import com.elhady.movies.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
}