package com.elhady.movies.ui.home

import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.enums.AllMediaType
import com.elhady.movies.domain.enums.HomeItemType
import com.elhady.movies.domain.usecases.home.GetAdventureMoviesUseCase
import com.elhady.movies.domain.usecases.home.GetMysteryMoviesUseCase
import com.elhady.movies.domain.usecases.home.GetNowPlayingMoviesUseCase
import com.elhady.movies.domain.usecases.home.GetPopularMoviesUseCase
import com.elhady.movies.domain.usecases.home.GetTopRatedMoviesUseCase
import com.elhady.movies.domain.usecases.home.GetTrendingMoviesUseCase
import com.elhady.movies.domain.usecases.home.GetUpcomingMoviesUseCase
import com.elhady.movies.domain.usecases.home.actor.GetTrendingActorsUseCase
import com.elhady.movies.domain.usecases.home.series.GetAiringTodaySeriesUseCase
import com.elhady.movies.domain.usecases.home.series.GetOnTheAirSeriesUseCase
import com.elhady.movies.domain.usecases.home.series.GetTVSeriesListsUseCase
import com.elhady.movies.ui.adapter.MediaInteractionListener
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.ui.home.adapters.ActorInteractionListener
import com.elhady.movies.ui.home.adapters.MovieInteractionListener
import com.elhady.movies.ui.home.adapters.TVSeriesInteractionListener
import com.elhady.movies.ui.home.homeUiState.HomeUiEvent
import com.elhady.movies.ui.home.homeUiState.HomeUiState
import com.elhady.movies.ui.mappers.ActorUiMapper
import com.elhady.movies.ui.mappers.MediaUiMapper
import com.elhady.movies.ui.mappers.PopularUiMapper
import com.elhady.movies.ui.models.MediaUiState
import com.elhady.movies.ui.models.PopularUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val popularUiMapper: PopularUiMapper,
    private val mediaUiMapper: MediaUiMapper,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getTrendingMovieUseCase: GetTrendingMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getOnTheAirSeriesUseCase: GetOnTheAirSeriesUseCase,
    private val getAiringTodaySeriesUseCase: GetAiringTodaySeriesUseCase,
    private val getTVSeriesListsUseCase: GetTVSeriesListsUseCase,
    private val getMysteryMoviesUseCase: GetMysteryMoviesUseCase,
    private val getAdventureMoviesUseCase: GetAdventureMoviesUseCase,
    private val getTrendingActorsUseCase: GetTrendingActorsUseCase,
    private val actorUiMapper: ActorUiMapper
) :
    BaseViewModel<HomeUiState, HomeUiEvent>(HomeUiState()), MovieInteractionListener,
    TVSeriesInteractionListener,
    ActorInteractionListener, MediaInteractionListener, HomeInteractionListener {

    init {
        getData()
    }

    override fun getData() {
        _state.update {
            it.copy(isLoading = true, error = emptyList())
        }
        getPopular()
        getUpcomingMovies()
        getTrendingMovie()
        getNowPlayingMovies()
        getTopRatedMovies()
        getOnTheAirSeries()
        getAiringTodaySeries()
        getTVSeriesLists()
        getMysteryMovies()
        getAdventureMovies()
        getTrendingActors()
    }

    /**
     *  Popular Movies
     */
    private fun getPopular(){
        tryToExecute(
            call =  {getPopularMoviesUseCase()} ,
            onSuccess = ::onSuccessPopularMovies,
            mapper = popularUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessPopularMovies(items :List<PopularUiState> ){
        _state.update { it.copy(popularMovie = HomeItem.Slider(items), isLoading = false, error = emptyList()) }
    }

    private fun onError(error: Throwable){
        val errors = _state.value.error.toMutableList()
        errors.add(error.message.toString())
        _state.update { it.copy(error = errors, isLoading = false) }
    }

    /**
     *  Upcoming Movies
     */
    private fun getUpcomingMovies() {
        tryToExecute(
            call = { getUpcomingMoviesUseCase() },
            onSuccess = ::onSuccessUpcoming,
            onError = ::onError,
            mapper = mediaUiMapper
        )
    }

    private fun onSuccessUpcoming(items: List<MediaUiState>) {
        _state.update { it.copy(upcomingMovie = HomeItem.Upcoming(items), isLoading = false, error = emptyList()) }
    }

    /**
     *  Trending Movies
     */
    private fun getTrendingMovie() {
        tryToExecute(
            call = { getTrendingMovieUseCase() },
            onError = ::onError,
            onSuccess = ::onSuccessTrending,
            mapper = mediaUiMapper
        )
    }

    private fun onSuccessTrending(items: List<MediaUiState>) {
        _state.update { it.copy(trendingMovie = HomeItem.Trending(items), isLoading = false, error = emptyList()) }
    }

    /**
     *  Now Playing Movies
     */
    private fun getNowPlayingMovies() {
        tryToExecute(
            call = { getNowPlayingMoviesUseCase() },
            onSuccess = ::onSuccessNowPlaying,
            onError = ::onError,
            mapper = mediaUiMapper
        )
    }

    private fun onSuccessNowPlaying(items: List<MediaUiState>) {
        _state.update { it.copy(nowPlayingMovie = HomeItem.NowPlaying(items), isLoading = false, error = emptyList()) }
    }

    /**
     *  Top Popular Movies
     */
    private fun getTopRatedMovies() {
        viewModelScope.launch {
            try {
                getTopRatedMoviesUseCase().collect { list ->
                    if (list.isNotEmpty()) {
                        val topRatedItems = list.map(mediaUiMapper::map)
                        _state.update {
                            it.copy(
                                topRatedMovie = HomeItem.TopRated(topRatedItems),
                                isLoading = false
                            )
                        }
                    }
                }
            } catch (throwable: Throwable) {
                onError(throwable.message.toString())
            }
        }
    }

    /**
     *  Mystery Movies
     */
    private fun getMysteryMovies() {
        viewModelScope.launch {
            try {
                getMysteryMoviesUseCase().collect { list ->
                    if (list.isNotEmpty()) {
                        val items = list.map {
                            mediaUiMapper.map(it)
                        }
                        _state.update {
                            it.copy(
                                mysteryMovies = HomeItem.Mystery(items),
                                isLoading = false
                            )
                        }
                    }
                }
            } catch (throwable: Throwable) {
                onError(throwable.message.toString())
            }
        }
    }

    /**
     *  Adventure Movies
     */
    private fun getAdventureMovies() {
        viewModelScope.launch {
            try {
                getAdventureMoviesUseCase().collect { list ->
                    if (list.isNotEmpty()) {
                        val items = list.map {
                            mediaUiMapper.map(it)
                        }
                        _state.update {
                            it.copy(
                                adventureMovies = HomeItem.Adventure(items),
                                isLoading = false
                            )
                        }
                    }
                }
            } catch (throwable: Throwable) {
                onError(throwable.message.toString())
            }
        }
    }

    /**
     *  On The Air Series
     */
    private fun getOnTheAirSeries() {
        viewModelScope.launch {
            try {
                getOnTheAirSeriesUseCase().collect { list ->
                    if (list.isNotEmpty()) {
                        val items = list.map(mediaUiMapper::map)
                        _state.update {
                            it.copy(
                                onTheAirSeries = HomeItem.OnTheAirSeries(items),
                                isLoading = false
                            )
                        }
                    }
                }
            } catch (throwable: Throwable) {
                onError(throwable.message.toString())
            }
        }
    }

    /**
     *  Airing Today Series
     */
    private fun getAiringTodaySeries() {
        viewModelScope.launch {
            try {
                getAiringTodaySeriesUseCase().collect { list ->
                    if (list.isNotEmpty()) {
                        val items = list.map {
                            mediaUiMapper.map(it)
                        }
                        _state.update {
                            it.copy(
                                airingTodaySeries = HomeItem.AiringTodaySeries(items),
                                isLoading = false
                            )
                        }
                    }
                }
            } catch (throwable: Throwable) {
                onError(throwable.message.toString())
            }
        }
    }

    /**
     *  TV Series Lists
     * * Popular
     * * Top Rated
     * * Latest
     */
    private fun getTVSeriesLists() {
        viewModelScope.launch {
            try {
                getTVSeriesListsUseCase().collect { list ->
                    if (list.isNotEmpty()) {
                        val items = list.map(mediaUiMapper::map)
                        _state.update {
                            it.copy(
                                tvSeriesLists = HomeItem.TVSeriesLists(items),
                                isLoading = false
                            )
                        }
                    }
                }
            } catch (throwable: Throwable) {
                onError(throwable.message.toString())
            }
        }
    }

    /**
     *  Trending Actors
     */
    private fun getTrendingActors() {
        viewModelScope.launch {
            try {
                getTrendingActorsUseCase().collect { list ->
                    if (list.isNotEmpty()) {
                        val actorItems = list.map {
                            actorUiMapper.map(it)
                        }
                        _state.update {
                            it.copy(
                                actors = HomeItem.Actor(actorItems),
                                isLoading = false
                            )
                        }
                    }
                }
            } catch (throwable: Throwable) {
                onError(throwable.message.toString())
            }
        }

    }

    private fun onError(error: String) {
        val errors = _state.value.error.toMutableList()
        errors.add(error)
        _state.update {
            it.copy(error = errors, isLoading = false)
        }
    }

    override fun onClickMovie(movieID: Int) {
        sendEvent(HomeUiEvent.ClickMovieEvent(movieID))
    }

    override fun onClickSeeAllMovies(mediaType: HomeItemType) {
        val type = when (mediaType) {
            HomeItemType.TRENDING -> AllMediaType.TRENDING
            HomeItemType.UPCOMING -> AllMediaType.UPCOMING
            HomeItemType.NOW_PLAYING -> AllMediaType.NOW_PLAYING
            HomeItemType.TOP_RATED -> AllMediaType.TOP_RATED_MOVIE
            HomeItemType.TOP_RATED_SERIES -> AllMediaType.TOP_RATED_TV
            HomeItemType.ON_THE_AIR_SERIES -> TODO()
            HomeItemType.MYSTERY -> AllMediaType.MYSTERY
            HomeItemType.ADVENTURE -> AllMediaType.ADVENTURE
            HomeItemType.ACTOR_MOVIES -> AllMediaType.ACTOR_MOVIES
        }
        sendEvent(HomeUiEvent.ClickSeeAllMoviesEvent(type))
    }

    override fun onClickTVSeries(seriesID: Int) {
        sendEvent(HomeUiEvent.ClickSeriesEvent(seriesID))
    }

    override fun onClickAllTVSeries(type: AllMediaType) {
        sendEvent(HomeUiEvent.ClickSeeAllSeriesEvent(type))
    }

    override fun onClickActor(actorID: Int) {
        sendEvent(HomeUiEvent.ClickActorEvent(actorID))
    }

    override fun onClickSeeAllActors() {
        sendEvent(HomeUiEvent.ClickSeeAllActorsEvent)
    }

    override fun onClickMedia(mediaId: Int) {
        sendEvent(HomeUiEvent.ClickSeriesEvent(mediaId))
    }

}