package com.elhady.movies.ui.movieDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.enums.HomeItemType
import com.elhady.movies.domain.usecases.movieDetails.GetMovieDetailsUseCase
import com.elhady.movies.ui.actorDetails.ActorMoviesUiMapper
import com.elhady.movies.ui.actors.ActorsUiState
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.ui.home.adapters.ActorInteractionListener
import com.elhady.movies.ui.home.adapters.MovieInteractionListener
import com.elhady.movies.ui.mappers.ActorUiMapper
import com.elhady.movies.ui.mappers.MediaUiMapper
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
    private val movieDetailsUiMapper: MovieDetailsUiMapper,
    private val actorUiMapper: ActorUiMapper,
    private val mediaUiMapper: MediaUiMapper
) : BaseViewModel(), DetailsInteractionListener, ActorInteractionListener, MovieInteractionListener {


    private val args = MovieDetailsFragmentArgs.fromSavedStateHandle(state)

    private val _detailsUiState = MutableStateFlow(DetailsUiState())
    val detailsUiState = _detailsUiState.asStateFlow()


    init {
        getData()
    }

    override fun getData() {
        getMovieDetails(args.movieID)
        getMovieCast(args.movieID)
        getSimilarMovies(args.movieID)
    }

    private fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            val movieDetailsResult = movieDetailsUiMapper.map(getMovieDetailsUseCase(movieId))
            _detailsUiState.update {
                it.copy(movieDetailsResult = DetailsItem.Header(movieDetailsResult))
            }
        }
    }

    private fun getMovieCast(movieId: Int) {
        viewModelScope.launch {
            val result = getMovieDetailsUseCase.getMovieCast(movieId = movieId).map {
                actorUiMapper.map(it)
            }
            _detailsUiState.update {
                it.copy(movieCastResult = DetailsItem.Cast(result))
            }
        }
    }

    private fun getSimilarMovies(movieId: Int) {
        viewModelScope.launch {
            val result = getMovieDetailsUseCase.getSimilarMovies(movieId = movieId).map {
                mediaUiMapper.map(it)
            }
            _detailsUiState.update {
                it.copy(similarMoviesResult = DetailsItem.Similar(result))
            }
        }
    }


    override fun onClickBackButton() {
        TODO("Not yet implemented")
    }

    override fun onClickActor(actorID: Int) {
        TODO("Not yet implemented")
    }

    override fun onClickMovie(movieID: Int) {
        TODO("Not yet implemented")
    }

    override fun onClickSeeAllMovies(mediaType: HomeItemType) {
        TODO("Not yet implemented")
    }


}