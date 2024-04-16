package com.elhady.movies.presentation.viewmodel.myrated

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.elhady.movies.core.bases.BaseViewModel
import com.elhady.movies.core.domain.usecase.usecase.myrated.GetMyRatedMoviesUseCase
import com.elhady.movies.core.domain.usecase.usecase.myrated.GetMyRatedTVShowsUseCase
import com.elhady.movies.presentation.viewmodel.common.listener.MovieListener
import com.elhady.movies.presentation.viewmodel.common.model.MovieHorizontalUIState
import com.elhady.movies.presentation.viewmodel.myrated.mappers.MyRatedMovieToMovieHorizontalUiMapper
import com.elhady.movies.presentation.viewmodel.myrated.mappers.MyRatedTvShowToMovieHorizontalUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MyRatedViewModel @Inject constructor(
    private val getRatedTVShowsUseCase: GetMyRatedTVShowsUseCase,
    private val getRatedMoviesUseCase: GetMyRatedMoviesUseCase,
    private val myRatedMovieToMovieHorizontalUiMapper: MyRatedMovieToMovieHorizontalUiMapper,
    private val myRatedTvShowToMovieHorizontalUiMapper: MyRatedTvShowToMovieHorizontalUiMapper,
) : BaseViewModel<MyRatedUiState, MyRatedEvents>(MyRatedUiState()), MyRatedListner,
    MovieListener {


    init {
        getData()
    }

    private fun getData() {
        when (_state.value.myRateType) {
            RateType.Movies -> fetchMyRatedMovies()
            RateType.TVShows -> fetchMyRatedTvShow()
        }
    }

    fun fetchMyRatedMovies() {
        _state.update { it.copy(isLoading = true) }
        wrapperPager(
            data = { getRatedMoviesUseCase() },
            onSuccess = ::onSuccessRatedMovie,
            mapper = myRatedMovieToMovieHorizontalUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessRatedMovie(myRatedMovieEntity: Flow<PagingData<MovieHorizontalUIState>>) {
        _state.update {
            it.copy(
                myRateType = RateType.Movies,
                myRatedMedia = myRatedMovieEntity,
                isLoading = false,
                errorList = emptyList()
            )
        }
    }

    fun fetchMyRatedTvShow() {
        _state.update { it.copy(isLoading = true) }
        wrapperPager(
            data = { getRatedTVShowsUseCase() },
            onSuccess = ::onSuccessRatedTvShow,
            mapper = myRatedTvShowToMovieHorizontalUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessRatedTvShow(myRatedTvShowEntity: Flow<PagingData<MovieHorizontalUIState>>) {
        _state.update {
            it.copy(
                myRateType = RateType.TVShows,
                myRatedMedia = myRatedTvShowEntity,
                isLoading = false,
                errorList = emptyList()
            )
        }
    }

    private fun onError(throwable: Throwable) {
        val errorMessage = throwable.message ?: "No network connection"
        _state.update {
            it.copy(
                errorList = listOf(errorMessage),
                isLoading = false
            )
        }
    }

    fun setErrorUiState(combinedLoadStates: CombinedLoadStates) {
        when (combinedLoadStates.refresh) {
            is LoadState.NotLoading -> {
                _state.update {
                    it.copy(isLoading = false, errorList = emptyList())
                }
            }

            LoadState.Loading -> {
                _state.update {
                    it.copy(isLoading = true, errorList = emptyList())
                }
            }

            is LoadState.Error -> {
                _state.update {
                    it.copy(isLoading = false, errorList = listOf("no Network"))
                }
            }
        }
    }

    override fun onBackPressed() {
        sendEvent(MyRatedEvents.NavigateBack)
    }

    override fun onClickMovieChip() {
        sendEvent(MyRatedEvents.ShowMyRatedMoviesPressed)
    }

    override fun onClickTvShowChip() {
        sendEvent(MyRatedEvents.ShowMyRatedTvShowPressed)
    }

    override fun onClickMedia(id: Int) {
        when (_state.value.myRateType) {
            RateType.Movies -> sendEvent(MyRatedEvents.NavigateToMovieDetails(id))
            RateType.TVShows -> sendEvent(MyRatedEvents.NavigateToTVShowDetails(id))
        }
    }
}