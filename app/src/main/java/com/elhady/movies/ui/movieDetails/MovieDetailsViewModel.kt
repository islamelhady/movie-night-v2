package com.elhady.movies.ui.movieDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.movieDetails.GetMovieDetailsUseCase
import com.elhady.movies.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    state: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val movieDetailsUiMapper: MovieDetailsUiMapper
) : BaseViewModel(), DetailsInteractionListener {


    private val args = MovieDetailsFragmentArgs.fromSavedStateHandle(state)

    private val _detailsUiState = MutableStateFlow(DetailsUiState())
    val detailsUiState = _detailsUiState.asStateFlow()


    init {
        getData()
    }

    override fun getData() {
        getMovieDetails(args.movieID)
    }

    private fun getMovieDetails(movieId: Int){
        viewModelScope.launch {
            val movieDetailsResult = movieDetailsUiMapper.map(getMovieDetailsUseCase(movieId))
            _detailsUiState.update {
                it.copy(movieDetailsResult = DetailsItem.Header(movieDetailsResult))
            }
        }
    }
    override fun onClickBackButton() {
        TODO("Not yet implemented")
    }


}