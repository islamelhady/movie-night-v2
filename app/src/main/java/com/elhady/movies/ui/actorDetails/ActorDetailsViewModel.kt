package com.elhady.movies.ui.actorDetails

import androidx.lifecycle.SavedStateHandle
import com.elhady.movies.domain.enums.HomeItemType
import com.elhady.movies.domain.models.ActorDetails
import com.elhady.movies.domain.usecases.GetActorDetailsUseCase
import com.elhady.movies.domain.usecases.GetActorsMoviesUseCase
import com.elhady.movies.ui.actorDetails.mapper.ActorDetailsUiMapper
import com.elhady.movies.ui.actorDetails.mapper.ActorMoviesUiMapper
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.ui.home.adapters.MovieInteractionListener
import com.elhady.movies.utilities.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ActorDetailsViewModel @Inject constructor(
    state: SavedStateHandle,
    private val getActorDetailsUseCase: GetActorDetailsUseCase,
    private val actorDetailsUiMapper: ActorDetailsUiMapper,
    private val getActorsMoviesUseCase: GetActorsMoviesUseCase,
    private val actorMoviesUiMapper: ActorMoviesUiMapper
) : BaseViewModel<ActorDetailsUiState>(ActorDetailsUiState()), MovieInteractionListener {

    val args = ActorDetailsFragmentArgs.fromSavedStateHandle(state)

    private val _uIState = MutableStateFlow(ActorDetailsUiState())
    val uiState = _uIState.asStateFlow()

    private val _uiEvent = MutableStateFlow<Event<ActorDetailsUiEvent>?>(null)
    val uiEvent = _uiEvent.asStateFlow()

    init {
        getData()
    }

    override fun getData() {
        _uIState.update { it.copy(isLoading = true, onError = emptyList()) }
        getActorInfo()
        getMoviesByActor()
    }

    private fun getActorInfo() {
        tryToExecute(
            call = { getActorDetailsUseCase(args.actorID) },
            onSuccess = ::onSuccessActorInfo,
            onError = ::onErrorGetActorData
        )
    }

    private fun onSuccessActorInfo(actorDetails: ActorDetails){
        val result = actorDetailsUiMapper.map(actorDetails)
        _uIState.update {
            it.copy(actorInfo = result, isLoading = false)
        }
    }

    private fun onErrorGetActorData(error: Throwable){
        val errors = _uIState.value.onError.toMutableList()
        errors.add(error.message.toString())
        _uIState.update { it.copy(onError = errors, isLoading = false) }

    }


    private fun getMoviesByActor(){
        tryToExecute(
            call = { getActorsMoviesUseCase(args.actorID) },
            onSuccess = ::onSuccessMoviesByActor,
            mapper = actorMoviesUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessMoviesByActor(actorMovies: List<ActorMoviesUiState>){
        _uIState.update {
            it.copy(actorMovies = actorMovies, isLoading = false)
        }
    }


    private fun onError(error: Throwable) {
        val errors = _uIState.value.onError.toMutableList()
        errors.add(error.message.toString())
        _uIState.update { it.copy(onError = errors, isLoading = false) }
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