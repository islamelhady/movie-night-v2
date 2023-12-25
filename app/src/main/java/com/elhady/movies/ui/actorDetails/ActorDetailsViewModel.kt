package com.elhady.movies.ui.actorDetails

import androidx.lifecycle.SavedStateHandle
import com.elhady.movies.domain.enums.SeeAllType
import com.elhady.movies.domain.usecases.GetActorDetailsUseCase
import com.elhady.movies.domain.usecases.GetActorsMoviesUseCase
import com.elhady.movies.ui.actorDetails.mapper.ActorDetailsUiMapper
import com.elhady.movies.ui.actorDetails.mapper.ActorMoviesUiMapper
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.ui.home.adapters.MovieInteractionListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ActorDetailsViewModel @Inject constructor(
    state: SavedStateHandle,
    private val getActorDetailsUseCase: GetActorDetailsUseCase,
    private val actorDetailsUiMapper: ActorDetailsUiMapper,
    private val getActorsMoviesUseCase: GetActorsMoviesUseCase,
    private val actorMoviesUiMapper: ActorMoviesUiMapper
) : BaseViewModel<ActorDetailsUiState, ActorDetailsUiEvent>(ActorDetailsUiState()),
    MovieInteractionListener {

    val args = ActorDetailsFragmentArgs.fromSavedStateHandle(state)

    init {
        getData()
    }

    override fun getData() {
        _state.update { it.copy(isLoading = true, onErrors = emptyList()) }
        getActorInfo()
        getMoviesByActor()
    }

    private fun getActorInfo() {
        tryToExecute(
            call = { getActorDetailsUseCase(args.actorID) },
            mapper = actorDetailsUiMapper,
            onSuccess = ::onSuccessActorInfo,
            onError = ::onError
        )
    }

    private fun onSuccessActorInfo(actorInfoUiState: ActorInfoUiState) {
        _state.update { it.copy(actorInfo = actorInfoUiState, isLoading = false, onErrors = emptyList()) }
    }


    private fun getMoviesByActor() {
        tryToExecute(
            call = { getActorsMoviesUseCase(args.actorID) },
            onSuccess = ::onSuccessMoviesByActor,
            mapper = actorMoviesUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessMoviesByActor(actorMovies: List<ActorMoviesUiState>) {
        _state.update {
            it.copy(actorMovies = actorMovies, isLoading = false, onErrors = emptyList())
        }
    }


    private fun onError(error: Throwable) {
        val errors = _state.value.onErrors.toMutableList()
        errors.add(error.message.toString())
        _state.update { it.copy(onErrors = errors, isLoading = false) }
    }

    override fun onClickMovie(movieID: Int) {
        sendEvent(ActorDetailsUiEvent.ClickMovieEvent(movieID = movieID))
    }

    override fun onClickSeeAllMovies(mediaType: SeeAllType) {
        sendEvent(ActorDetailsUiEvent.ClickSeeAllEvent)
    }

    fun onClickBackButton() {
        sendEvent(ActorDetailsUiEvent.ClickBackButton)
    }


}