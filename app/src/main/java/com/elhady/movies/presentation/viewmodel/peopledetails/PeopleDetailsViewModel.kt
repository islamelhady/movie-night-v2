package com.elhady.movies.presentation.viewmodel.peopledetails

import androidx.lifecycle.SavedStateHandle
import com.elhady.movies.core.bases.BaseViewModel
import com.elhady.movies.core.domain.usecase.people.GetMoviesByPersonUseCase
import com.elhady.movies.core.domain.usecase.people.GetPeopleDetailsUseCase
import com.elhady.movies.core.domain.usecase.tvdetails.GetTvShowsByPersonUseCase
import com.elhady.movies.presentation.viewmodel.peopledetails.mapper.MoviesByPeopleUiMapper
import com.elhady.movies.presentation.viewmodel.peopledetails.mapper.PeopleDataUiMapper
import com.elhady.movies.presentation.viewmodel.peopledetails.mapper.TvShowsByPeopleUiMapper
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
) : BaseViewModel<PersonDetailsUiState, PeopleDetailsUiEvent>(PersonDetailsUiState()),
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
            onSuccess = ::onSuccessGetPersonData,
            mapper = peopleDataUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessGetPersonData(personInfoUiState: PersonDetailsUiState.PersonInfoUiState) {
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
            onSuccess = ::onSuccessGetMoviesByPeople,
            onError = ::onError,
            mapper = moviesByPeopleUiMapper
        )
    }


    private fun onSuccessGetMoviesByPeople(list: List<PersonDetailsUiState.PeopleMediaUiState>) {
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
            onSuccess = ::onSuccessGetTvShowsByPeople,
            onError = ::onError,
            mapper = tvShowsByPeopleUiMapper
        )
    }


    private fun onSuccessGetTvShowsByPeople(list: List<PersonDetailsUiState.PeopleMediaUiState>) {
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


