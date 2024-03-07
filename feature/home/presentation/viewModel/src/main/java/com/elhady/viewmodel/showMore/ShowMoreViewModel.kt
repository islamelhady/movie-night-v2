package com.elhady.viewmodel.showMore

import androidx.lifecycle.SavedStateHandle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.elhady.base.BaseViewModel
import com.elhady.usecase.showMore.actor.GetMorePopularActorByTypeUseCase
import com.elhady.usecase.showMore.movies.GetMoreAdventureMoviesByTypeUseCase
import com.elhady.usecase.showMore.movies.GetMoreMysteryMoviesByTypeUseCase
import com.elhady.usecase.showMore.movies.GetMoreNowPlayingMoviesByTypeUseCase
import com.elhady.usecase.showMore.movies.GetMoreTopRatedByTypeUseCase
import com.elhady.usecase.showMore.movies.GetMoreTrendingByTypeUseCase
import com.elhady.usecase.showMore.movies.GetMoreUpcomingMoviesByTypeUseCase
import com.elhady.usecase.showMore.tvShows.GetMoreLatestTvShowsByTypeUseCase
import com.elhady.usecase.showMore.tvShows.GetMoreOnTheAirTvShowsByTypeUseCase
import com.elhady.usecase.showMore.tvShows.GetMorePopularTvShowsByTypeUseCase
import com.elhady.usecase.showMore.tvShows.GetMoreTopRatedTvShowsByTypeUseCase
import com.elhady.viewmodel.showMore.mappers.ShowMoreActorsUiMapper
import com.elhady.viewmodel.showMore.mappers.ShowMoreMoviesUiMapper
import com.elhady.viewmodel.showMore.mappers.ShowMoreTvShowsUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ShowMoreViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMoreUpcomingMoviesByTypeUseCase: GetMoreUpcomingMoviesByTypeUseCase,
    private val getMoreAdventureMoviesByTypeUseCase: GetMoreAdventureMoviesByTypeUseCase,
    private val getMoreMysteryMoviesByTypeUseCase: GetMoreMysteryMoviesByTypeUseCase,
    private val getMoreNowPlayingMoviesByTypeUseCase: GetMoreNowPlayingMoviesByTypeUseCase,
    private val getShowMoreTopRatedByTypeUseCase: GetMoreTopRatedByTypeUseCase,
    private val getShowMoreTrendingByTypeUseCase: GetMoreTrendingByTypeUseCase,
    private val getMorePopularActorByTypeUseCase: GetMorePopularActorByTypeUseCase,
    private val getMoreOnTheAirTvShowsByTypeUseCase: GetMoreOnTheAirTvShowsByTypeUseCase,
    private val getMoreLatestTvShowsByTypeUseCase: GetMoreLatestTvShowsByTypeUseCase,
    private val getMoreTopRatedTvShowsByTypeUseCase: GetMoreTopRatedTvShowsByTypeUseCase,
    private val getMorePopularTvShowsByTypeUseCase: GetMorePopularTvShowsByTypeUseCase,
    private val showMoreMoviesUiMapper: ShowMoreMoviesUiMapper,
    private val showMoreActorsUiMapper: ShowMoreActorsUiMapper,
    private val showMoreTvShowsUiMapper: ShowMoreTvShowsUiMapper
) : BaseViewModel<ShowMoreUiState, SeeAllMediaUiEvent>(ShowMoreUiState()),
    ShowMoreListener{


    init {
        _state.update { it.copy(isLoading = true) }
        getData()
    }

    fun getData() {
        when (_state.value.showMoreType) {
            ShowMoreType.TOP_RATED_TV_SHOW -> getTopRatedTvShowMore()
            ShowMoreType.POPULAR_TV_SHOW -> getPopularTvShowMore()
            ShowMoreType.LATEST_TV_SHOW -> getLatestTvShowMore()
            ShowMoreType.ON_THE_AIR_TV_SHOW -> getOnTheAirTvShowMore()
            ShowMoreType.UPCOMING_MOVIES -> getUpcomingMoviesShowMore()
            ShowMoreType.TRENDING_MOVIES -> getTrendingMoviesShowMore()
            ShowMoreType.NOW_PLAYING_MOVIES -> getNowPlayingMoviesShowMore()
            ShowMoreType.TOP_RATED_MOVIES -> getTopRatedMoviesShowMore()
            ShowMoreType.MYSTERY_MOVIES -> getMysteryMoviesShowMore()
            ShowMoreType.ADVENTURE_MOVIES -> getAdventureMoviesShowMore()
            ShowMoreType.POPULAR_ACTORS -> getPopularActorShowMore()
        }
    }


    private fun getOnTheAirTvShowMore(){
        wrapperPager(
            data = { getMoreOnTheAirTvShowsByTypeUseCase() },
            mapper = showMoreTvShowsUiMapper::map,
            onSuccess = ::onSuccessOnTheAirTvShows,
            onError = ::onError
        )
    }

    private fun onSuccessOnTheAirTvShows(onTheAirTvShows: Flow<PagingData<ShowMoreUi>>){
        _state.update {
            it.copy(
                showMoreOnTheAirMovies = onTheAirTvShows,
                isLoading = false, onErrors = emptyList(), showMoreType = ShowMoreType.ON_THE_AIR_TV_SHOW
            )
        }
    }

   private fun getLatestTvShowMore(){
       wrapperPager(
           data = { getMoreLatestTvShowsByTypeUseCase() },
           mapper = showMoreTvShowsUiMapper::map,
           onSuccess = ::onSuccessLatestTvShows,
           onError = ::onError
       )
    }

    private fun onSuccessLatestTvShows(latestTvShows: Flow<PagingData<ShowMoreUi>>){
        _state.update {
            it.copy(
                showMoreLatestTvShows = latestTvShows,
                isLoading = false, onErrors = emptyList(), showMoreType = ShowMoreType.LATEST_TV_SHOW
            )
        }
    }
    private fun getPopularTvShowMore(){
        wrapperPager(
            data = { getMorePopularTvShowsByTypeUseCase() },
            mapper = showMoreTvShowsUiMapper::map,
            onSuccess = ::onSuccessPopularTvShows,
            onError = ::onError
        )
    }

    private fun onSuccessPopularTvShows(popularTvShows: Flow<PagingData<ShowMoreUi>>) {
        _state.update {
            it.copy(
                showMorePopularTvShows = popularTvShows,
                isLoading = false, onErrors = emptyList(), showMoreType = ShowMoreType.POPULAR_TV_SHOW
            )
        }
    }

    private fun getTopRatedTvShowMore(){
        wrapperPager(
            data = { getMoreTopRatedTvShowsByTypeUseCase() },
            mapper = showMoreTvShowsUiMapper::map,
            onSuccess = ::onSuccessTopRatedTvShows,
            onError = ::onError
        )
    }

    private fun onSuccessTopRatedTvShows(topRatedTvShows: Flow<PagingData<ShowMoreUi>>) {
        _state.update {
            it.copy(
                showMoreTopRatedTvShows = topRatedTvShows,
                isLoading = false, onErrors = emptyList(), showMoreType = ShowMoreType.TOP_RATED_TV_SHOW
            )
        }
    }
    private fun getPopularActorShowMore(){
        wrapperPager(
            data = { getMorePopularActorByTypeUseCase() },
            mapper = showMoreActorsUiMapper::map,
            onSuccess = ::onSuccessPopularActor,
            onError = ::onError
        )
    }

    private fun onSuccessPopularActor(popularActor: Flow<PagingData<ShowMoreUi>>) {
        _state.update {
            it.copy(
                showMorePopularActorMovies = popularActor,
                isLoading = false, onErrors = emptyList(), showMoreType = ShowMoreType.POPULAR_ACTORS
            )
        }
    }


    private fun getUpcomingMoviesShowMore() {
        wrapperPager(
            data = { getMoreUpcomingMoviesByTypeUseCase() },
            mapper = showMoreMoviesUiMapper::map,
            onSuccess = ::onSuccessUpcomingMovies,
            onError = ::onError
        )
    }

    private fun onSuccessUpcomingMovies(upcomingMovies: Flow<PagingData<ShowMoreUi>>) {
        _state.update {
            it.copy(
                showMoreUpcomingMovies = upcomingMovies,
                isLoading = false, onErrors = emptyList(), showMoreType = ShowMoreType.UPCOMING_MOVIES
            )
        }
    }

    private fun getTrendingMoviesShowMore() {
        wrapperPager(
            data = { getShowMoreTrendingByTypeUseCase() },
            mapper = showMoreMoviesUiMapper::map,
            onSuccess = ::onSuccessTrendingMovies,
            onError = ::onError
        )
    }

    private fun onSuccessTrendingMovies(trendingMovies: Flow<PagingData<ShowMoreUi>>) {
        _state.update {
            it.copy(
                showMoreTrendingMovies = trendingMovies,
                isLoading = false, onErrors = emptyList(), showMoreType = ShowMoreType.TRENDING_MOVIES
            )
        }
    }


    private fun getNowPlayingMoviesShowMore() {
        wrapperPager(
            data = { getMoreNowPlayingMoviesByTypeUseCase() },
            mapper = showMoreMoviesUiMapper::map,
            onSuccess = ::onSuccessNowPlayingMovies,
            onError = ::onError
        )
    }

    private fun onSuccessNowPlayingMovies(nowPlayingMovies: Flow<PagingData<ShowMoreUi>>) {
        _state.update {
            it.copy(
                showMoreNowPlayingMovies = nowPlayingMovies,
                isLoading = false, onErrors = emptyList(), showMoreType = ShowMoreType.NOW_PLAYING_MOVIES
            )
        }
    }

    private fun getAdventureMoviesShowMore() {
        wrapperPager(
            data = { getMoreAdventureMoviesByTypeUseCase() },
            mapper = showMoreMoviesUiMapper::map,
            onSuccess = ::onSuccessAdventureMoviesMovies,
            onError = ::onError
        )
    }

    private fun onSuccessAdventureMoviesMovies(adventureMovies: Flow<PagingData<ShowMoreUi>>) {
        _state.update {
            it.copy(
                showMoreAdventureMovies = adventureMovies,
                isLoading = false, onErrors = emptyList(), showMoreType = ShowMoreType.ADVENTURE_MOVIES
            )
        }
    }

    private fun getMysteryMoviesShowMore() {
        wrapperPager(
            data = { getMoreMysteryMoviesByTypeUseCase() },
            mapper = showMoreMoviesUiMapper::map,
            onSuccess = ::onSuccessMysteryMoviesMovies,
            onError = ::onError
        )
    }

    private fun onSuccessMysteryMoviesMovies(mysteryMovies: Flow<PagingData<ShowMoreUi>>) {
        _state.update {
            it.copy(
                showMoreMysteryMovies = mysteryMovies,
                isLoading = false, onErrors = emptyList(), showMoreType = ShowMoreType.MYSTERY_MOVIES
            )
        }
    }

    private fun getTopRatedMoviesShowMore() {
        wrapperPager(
            data = { getShowMoreTopRatedByTypeUseCase() },
            mapper = showMoreMoviesUiMapper::map,
            onSuccess = ::onSuccessTopRatedMoviesMovies,
            onError = ::onError
        )
    }

    private fun onSuccessTopRatedMoviesMovies(topRatedMovies: Flow<PagingData<ShowMoreUi>>) {
        _state.update {
            it.copy(
                showMoreTopRatedMovies = topRatedMovies,
                isLoading = false, onErrors = emptyList(), showMoreType = ShowMoreType.TOP_RATED_MOVIES
            )
        }
    }
    private fun onError(throwable: Throwable) {
        val errors = _state.value.onErrors.toMutableList()
        errors.add(throwable.message ?: "No network connection ")
        showErrorWithSnackBar(errors.toString())
        _state.update {
            it.copy(
                onErrors = errors,
                isLoading = false
            )
        }
    }

    private fun showErrorWithSnackBar(messages: String) {
        sendEvent(SeeAllMediaUiEvent.ShowSnackBar(messages))
    }


    fun setErrorUiState(combinedLoadStates: CombinedLoadStates) {
        when (combinedLoadStates.refresh) {
            is LoadState.NotLoading -> {
                _state.update {
                    it.copy(isLoading = false, onErrors = emptyList())
                }
            }

            is LoadState.Loading -> {
                _state.update {
                    it.copy(isLoading = true, onErrors = emptyList())
                }
            }

            is LoadState.Error -> {
                _state.update {
                    it.copy(isLoading = false, onErrors = listOf("No network"))
                }
            }
        }

    }

    override fun onClickMedia(mediaId: Int) {
//        if (checkMediaTypeUseCase(args.type)) {
//            sendEvent(SeeAllMediaUiEvent.ClickSeriesEvent(mediaId))
//        } else {
//            sendEvent(SeeAllMediaUiEvent.ClickMovieEvent(mediaId))
//        }
    }

    override fun backNavigate() {
        TODO("Not yet implemented")
    }


}