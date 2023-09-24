package com.elhady.movies.ui.favorite

import androidx.lifecycle.ViewModel
import com.elhady.movies.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject  constructor(private val repository: MovieRepository) : ViewModel() {
}