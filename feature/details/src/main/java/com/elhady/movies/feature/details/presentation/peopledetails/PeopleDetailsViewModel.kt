package com.elhady.movies.feature.details.presentation.peopledetails

import androidx.lifecycle.SavedStateHandle
import com.elhady.movies.core.common.AppException
import com.elhady.movies.core.domain.usecase.people.GetMoviesByPersonUseCase
import com.elhady.movies.core.domain.usecase.people.GetPeopleDetailsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetTvShowsByPersonUseCase
import com.elhady.movies.core.ui.base.BaseViewModel
import com.elhady.movies.core.ui.base.toErrorUiState
import com.elhady.movies.feature.details.presentation.peopledetails.mapper.MoviesByPeopleUiMapper
import com.elhady.movies.feature.details.presentation.peopledetails.mapper.PeopleDataUiMapper
import com.elhady.movies.feature.details.presentation.peopledetails.mapper.TvShowsByPeopleUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PeopleDetailsViewModel @Inject constructor(
    private val getPeopleDetailsUseCase: GetPeopleDetailsUseCase,
    private val getMoviesByPersonUseCase: GetMoviesByPersonUseCase,
    private val getTvShowsByPersonUseCase: GetTvShowsByPersonUseCase,
    private val peopleDataUiMapper: PeopleDataUiMapper,
    private val moviesByPeopleUiMapper: MoviesByPeopleUiMapper,
    private val tvShowsByPeopleUiMapper: TvShowsByPeopleUiMapper,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<PeopleDetailsUiState, PeopleDetailsUiEffect>(
    PeopleDetailsUiState()
) {

    private val personId: Int =
        checkNotNull(savedStateHandle.get<Int>(PERSON_ID))

    init {
        refreshScreen()
    }

    fun onEvent(event: PeopleDetailsUiEvent) {
        when (event) {
            PeopleDetailsUiEvent.BackClicked -> {
                sendEffect(PeopleDetailsUiEffect.NavigateBack)
            }

            is PeopleDetailsUiEvent.MovieClicked -> {
                sendEffect(
                    PeopleDetailsUiEffect.NavigateToMovieDetails(
                        event.movieId
                    )
                )
            }

            is PeopleDetailsUiEvent.TvShowClicked -> {
                sendEffect(
                    PeopleDetailsUiEffect.NavigateToTvDetails(
                        event.tvShowId
                    )
                )
            }

            PeopleDetailsUiEvent.RetryClicked -> {
                refreshScreen()
            }
        }
    }

    private fun refreshScreen() {
        _state.update {
            PeopleDetailsUiState(
                isPersonLoading = true,
                isMoviesLoading = true,
                isTvShowsLoading = true,
            )
        }

        getPersonData()
        getMoviesByPerson()
        getTvShowsByPerson()
    }

    private fun getPersonData() {
        tryToExecute(
            call = {
                getPeopleDetailsUseCase(personId)
            },
            mapper = peopleDataUiMapper,
            onSuccess = ::onSuccessGetPersonData,
            onError = ::onErrorGetPersonData,
        )
    }

    private fun onSuccessGetPersonData(
        personInfo: PeopleDetailsUiState.PersonInfoUiState,
    ) {
        _state.update {
            it.copy(
                peopleData = personInfo,
                isPersonLoading = false,
            )
        }
    }

    private fun getMoviesByPerson() {
        tryToExecute(
            call = {
                getMoviesByPersonUseCase(personId)
            },
            mapper = moviesByPeopleUiMapper,
            onSuccess = ::onSuccessGetMovies,
            onError = ::onErrorGetMovies,
        )
    }

    private fun onSuccessGetMovies(
        movies: List<PeopleDetailsUiState.PeopleMediaUiState>,
    ) {
        _state.update {
            it.copy(
                movies = movies,
                isMoviesLoading = false,
            )
        }
    }

    private fun getTvShowsByPerson() {
        tryToExecute(
            call = {
                getTvShowsByPersonUseCase(personId)
            },
            mapper = tvShowsByPeopleUiMapper,
            onSuccess = ::onSuccessGetTvShows,
            onError = ::onErrorGetTvShows,
        )
    }

    private fun onSuccessGetTvShows(
        tvShows: List<PeopleDetailsUiState.PeopleMediaUiState>,
    ) {
        _state.update {
            it.copy(
                tvShows = tvShows,
                isTvShowsLoading = false,
            )
        }
    }

    private fun onErrorGetPersonData(error: AppException) {
        handleError(
            error = error,
            update = {
                it.copy(isPersonLoading = false)
            },
        )
    }

    private fun onErrorGetMovies(error: AppException) {
        handleError(
            error = error,
            update = {
                it.copy(isMoviesLoading = false)
            },
        )
    }

    private fun onErrorGetTvShows(error: AppException) {
        handleError(
            error = error,
            update = {
                it.copy(isTvShowsLoading = false)
            },
        )
    }

    private fun handleError(
        error: AppException,
        update: (PeopleDetailsUiState) -> PeopleDetailsUiState,
    ) {
        val errorUiState = error.toErrorUiState()

        _state.update { state ->
            update(
                state.copy(error = errorUiState)
            )
        }

        sendEffect(
            PeopleDetailsUiEffect.ShowSnackBar(
                errorUiState.messageRes.toString()
            )
        )
    }

    companion object {
        private const val PERSON_ID = "personId"
    }
}