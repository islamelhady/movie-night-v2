package com.elhady.movies.feature.details.presentation.peopledetails

import androidx.lifecycle.SavedStateHandle
import com.elhady.movies.core.ui.base.BaseViewModel
import com.elhady.movies.core.domain.usecase.people.GetMoviesByPersonUseCase
import com.elhady.movies.core.domain.usecase.people.GetPeopleDetailsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetTvShowsByPersonUseCase
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
    savedStateHandle: SavedStateHandle
) : BaseViewModel<PeopleDetailsUiState, PeopleDetailsUiEvent>(PeopleDetailsUiState()),
    PeopleDetailsListener {

    private val personId = savedStateHandle.get<Int>("personId") ?: 3

    init {
        refreshScreen()
    }

    fun refreshScreen() {
        _state.update { it.copy(onErrors = emptyList(), isLoading = true) }
        getPersonData()
        getMoviesByPeople()
        getTvShowsByPeople()
    }

    private fun getPersonData() {
        tryToExecute(
            call = { getPeopleDetailsUseCase(personId) },
            mapper = peopleDataUiMapper,
            onSuccess = ::onSuccessGetPersonData,
            onError = ::onError
        )
    }

    private fun onSuccessGetPersonData(personInfoUiState: PeopleDetailsUiState.PersonInfoUiState) {
        _state.update {
            it.copy(
                peopleData = personInfoUiState,
                isLoading = false,
                onErrors = emptyList()
            )
        }
    }

    private fun getMoviesByPeople() {
        tryToExecute(
            call = { getMoviesByPersonUseCase.invoke(personId) },
            mapper = moviesByPeopleUiMapper,
            onSuccess = ::onSuccessGetMoviesByPeople,
            onError = ::onError
        )
    }


    private fun onSuccessGetMoviesByPeople(list: List<PeopleDetailsUiState.PeopleMediaUiState>) {
        _state.update {
            it.copy(
                movies = list,
                isLoading = false,
                onErrors = emptyList()
            )
        }
    }

    private fun getTvShowsByPeople() {
        tryToExecute(
            call = { getTvShowsByPersonUseCase(personId) },
            mapper = tvShowsByPeopleUiMapper,
            onSuccess = ::onSuccessGetTvShowsByPeople,
            onError = ::onError
        )
    }


    private fun onSuccessGetTvShowsByPeople(list: List<PeopleDetailsUiState.PeopleMediaUiState>) {
        _state.update {
            it.copy(
                tvShows = list,
                isLoading = false,
                onErrors = emptyList()
            )
        }
    }

    private fun onError(e: Throwable) {
        val errors = _state.value.onErrors.toMutableList()
        errors.add(e.message.toString())
        _state.update { it.copy(onErrors = errors, isLoading = false) }
    }

    override fun onClickMedia(itemId: Int, type: String) {
        if (type == "movies") {
            sendEvent(PeopleDetailsUiEvent.ClickMovieEvent(itemId))
        } else if (type == "tvShows") {
            sendEvent(PeopleDetailsUiEvent.ClickTvShowsEvent(itemId))
        }
    }

    override fun backNavigate() {
        sendEvent(PeopleDetailsUiEvent.BackNavigate)
    }
}


