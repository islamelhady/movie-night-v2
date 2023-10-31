package com.elhady.movies.ui.actorDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.enums.HomeItemType
import com.elhady.movies.domain.usecases.GetActorDetailsUseCase
import com.elhady.movies.domain.usecases.GetActorsMoviesUseCase
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.ui.home.adapters.MovieInteractionListener
import com.elhady.movies.utilities.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorDetailsViewModel @Inject constructor(
    state: SavedStateHandle,
    private val getActorDetailsUseCase: GetActorDetailsUseCase,
    private val actorDetailsUiMapper: ActorDetailsUiMapper,
    private val getActorsMoviesUseCase: GetActorsMoviesUseCase,
    private val actorMoviesUiMapper: ActorMoviesUiMapper
) : BaseViewModel(), MovieInteractionListener {

    val args = ActorDetailsFragmentArgs.fromSavedStateHandle(state)

    private val _uIState = MutableStateFlow(ActorDetailsUiState())
    val uiState = _uIState.asStateFlow()

    private val _uiEvent = MutableStateFlow<Event<ActorDetailsUiEvent>?>(null)
    val uiEvent = _uiEvent.asStateFlow()

    init {
        getData()
    }

    override fun getData() {
        _uIState.update { it.copy(isLoading = true, error = emptyList()) }
        getActorDetails()
    }

    private fun getActorDetails() {
        viewModelScope.launch {
            try {
                val actorDetails = actorDetailsUiMapper.map(getActorDetailsUseCase(args.actorID))
                val actorMovies = getActorsMoviesUseCase(args.actorID).map {
                    actorMoviesUiMapper.map(it)
                }

                _uIState.update {
                    it.copy(
                        id = actorDetails.id,
                        name = actorDetails.name,
                        image = actorDetails.image,
                        placeOfBirth = actorDetails.placeOfBirth,
                        birthday = actorDetails.birthday,
                        biography = actorDetails.biography,
                        knownForDepartment = actorDetails.knownForDepartment,
                        gender = actorDetails.gender,
                        actorMovies = actorMovies,
                        isLoading = false,
                        isSuccess = true
                    )
                }
            } catch (error: Throwable) {
                onError(error.message.toString())
            }
        }
    }

    private fun onError(message: String) {
        _uIState.update { it.copy(error = listOf(Error(message = message)), isLoading = false) }
    }

    override fun onClickMovie(movieID: Int) {
        _uiEvent.update {
            Event(ActorDetailsUiEvent.ClickMovieEvent(movieID = movieID))
        }
    }

    override fun onClickSeeAllMovies(mediaType: HomeItemType) {
        _uiEvent.update {
            Event(ActorDetailsUiEvent.ClickSeeAllEvent)
        }
    }

    fun onClickBackButton(){
        _uiEvent.update {
            Event(ActorDetailsUiEvent.ClickBackButton)
        }
    }


}