package com.elhady.movies.feature.watchlist.presentation.myrated

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.elhady.movies.core.ui.base.BaseViewModel
import com.elhady.movies.core.domain.usecase.account.GetMyRatedMoviesUseCase
import com.elhady.movies.core.domain.usecase.account.GetMyRatedTvShowUseCase
import com.elhady.movies.core.ui.interaction.MovieAdapterListener
import com.elhady.movies.core.ui.state.MovieHorizontalUiState
import com.elhady.movies.feature.watchlist.presentation.myrated.mapper.MyRatedMovieToMovieHorizontalUiMapper
import com.elhady.movies.feature.watchlist.presentation.myrated.mapper.MyRatedTvShowToMovieHorizontalUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MyRatedViewModel @Inject constructor(
    private val getMyRatedTvShowUseCase: GetMyRatedTvShowUseCase,
    private val getMyRatedMoviesUseCase: GetMyRatedMoviesUseCase,
    private val myRatedMovieToMovieHorizontalUiMapper: MyRatedMovieToMovieHorizontalUiMapper,
    private val myRatedTvShowToMovieHorizontalUiMapper: MyRatedTvShowToMovieHorizontalUiMapper,
) : BaseViewModel<MyRatedUiState, MyRatedUiEvent>(MyRatedUiState()), MyRatedListener,
    MovieAdapterListener {


    init {
        getData()
    }

    private fun getData() {
        when (_state.value.myRateType) {
            RateType.Movies -> fetchMyRatedMovies()
            RateType.TvShows -> fetchMyRatedTvShow()
        }
    }

    fun fetchMyRatedMovies() {
        _state.update { it.copy(isLoading = true) }
        wrapperPager(
            data = { getMyRatedMoviesUseCase() },
            onSuccess = ::onSuccessRatedMovie,
            mapper = myRatedMovieToMovieHorizontalUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessRatedMovie(myRatedMovieEntity: Flow<PagingData<MovieHorizontalUiState>>) {
        _state.update {
            it.copy(
                myRateType = RateType.Movies,
                movies = myRatedMovieEntity,
                isLoading = false,
                errorList = emptyList()
            )
        }
    }

    fun fetchMyRatedTvShow() {
        _state.update { it.copy(isLoading = true) }
        wrapperPager(
            data = { getMyRatedTvShowUseCase() },
            onSuccess = ::onSuccessRatedTvShow,
            mapper = myRatedTvShowToMovieHorizontalUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessRatedTvShow(myRatedTvShowEntity: Flow<PagingData<MovieHorizontalUiState>>) {
        _state.update {
            it.copy(
                myRateType = RateType.TvShows,
                movies = myRatedTvShowEntity,
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
        sendEffect(MyRatedUiEvent.NavigateBack)
    }

    override fun onClickMovieChip() {
        sendEffect(MyRatedUiEvent.ShowMyRatedMoviesPressed)
    }

    override fun onClickTvShowChip() {
        sendEffect(MyRatedUiEvent.ShowMyRatedTvShowPressed)
    }

    override fun onClickMedia(id: Int) {
        when (_state.value.myRateType) {
            RateType.Movies -> sendEffect(MyRatedUiEvent.NavigateToMovieDetails(id))
            RateType.TvShows -> sendEffect(MyRatedUiEvent.NavigateToTvShowDetails(id))
        }
    }
}
