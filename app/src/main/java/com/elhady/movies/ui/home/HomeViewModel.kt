package com.elhady.movies.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import com.elhady.movies.ui.home.adapters.MovieInteractionListener
import com.elhady.movies.ui.home.adapters.AiringTodayInteractionListener
import com.elhady.movies.ui.home.homeUiState.HomeUiEvent
import com.elhady.movies.ui.home.homeUiState.HomeUiState
import com.elhady.movies.ui.mappers.ActorUiMapper
import com.elhady.movies.ui.mappers.MediaUiMapper
import com.elhady.movies.ui.mappers.PopularUiMapper
import com.elhady.movies.utilities.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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
    ViewModel(), MovieInteractionListener, AiringTodayInteractionListener {

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    private val _homeUiEvent = MutableStateFlow<Event<HomeUiEvent?>>(Event(null))
    val homeUiEvent = _homeUiEvent.asStateFlow()

    init {
        _homeUiState.update {
            it.copy(isLoading = true)
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
    private fun getPopular() {
        viewModelScope.launch {
            try {
                getPopularMoviesUseCase().collect { items ->
                    if (items.isNotEmpty()) {
                        val popularUiState = items.map(popularUiMapper::map)
                        _homeUiState.update {
                            it.copy(
                                popularMovie = HomeItem.Slider(popularUiState),
                                isLoading = false
                            )
                        }
                    }
                }

            } catch (e: Exception) {
                Log.d("ViewModel", e.message.toString())
            }
        }
    }

    /**
     *  Upcoming Movies
     */
    private fun getUpcomingMovies() {
        viewModelScope.launch {
            try {
                getUpcomingMoviesUseCase().collect { items ->
                    if (items.isNotEmpty()) {
                        val upcomingItems = items.map(mediaUiMapper::map)
                        _homeUiState.update {
                            it.copy(
                                upcomingMovie = HomeItem.Upcoming(upcomingItems),
                                isLoading = false
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d("upcoming", e.message.toString())
            }
        }
    }

    /**
     *  Trending Movies
     */
    private fun getTrendingMovie() {
        viewModelScope.launch {
            try {
                getTrendingMovieUseCase().collect { items ->
                    if (items.isNotEmpty()) {
                        val trendingItems = items.map(mediaUiMapper::map)
                        _homeUiState.update {
                            it.copy(
                                trendingMovie = HomeItem.Trending(trendingItems),
                                isLoading = false
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                Log.i("", e.message.toString())
            }
        }
    }

    /**
     *  Now Playing Movies
     */
    private fun getNowPlayingMovies() {
        viewModelScope.launch {
            try {
                getNowPlayingMoviesUseCase().collect { items ->
                    if (items.isNotEmpty()) {
                        val nowPlayingItems = items.map(mediaUiMapper::map)
                        _homeUiState.update {
                            it.copy(
                                nowPlayingMovie = HomeItem.NowPlaying(nowPlayingItems),
                                isLoading = false
                            )
                        }
                    }

                }
            } catch (e: Exception) {
                Log.i("", "")
            }
        }
    }

    /**
     *  Top Popular Movies
     */
    private fun getTopRatedMovies() {
        viewModelScope.launch {
            try {
                getTopRatedMoviesUseCase().collect { items ->
                    if (items.isNotEmpty()) {
                        val topRatedItems = items.map(mediaUiMapper::map)
                        _homeUiState.update {
                            it.copy(
                                topRatedMovie = HomeItem.TopRated(topRatedItems),
                                isLoading = false
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                Log.i("", "")
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
                    val mysteryItems = list.map {
                        mediaUiMapper.map(it)
                    }
                    _homeUiState.update {
                        it.copy(mysteryMovies = HomeItem.Mystery(mysteryItems))
                    }
                }
            } catch (e: Exception) {
                Log.i("", "")
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
                    val adventureItems = list.map {
                        mediaUiMapper.map(it)
                    }
                    _homeUiState.update {
                        it.copy(adventureMovies = HomeItem.Adventure(adventureItems))
                    }
                }
            } catch (e: Exception) {
                Log.i("", "")
            }
        }
    }

    /**
     *  On The Air Series
     */
    private fun getOnTheAirSeries() {
        viewModelScope.launch {
            try {
                getOnTheAirSeriesUseCase().collect { items ->
                    val onTheAirItems = items.map(mediaUiMapper::map)
                    _homeUiState.update {
                        it.copy(
                            onTheAirSeries = HomeItem.OnTheAirSeries(onTheAirItems)
                        )
                    }
                }
            } catch (e: Exception) {
                Log.i("", "")
            }
        }
    }

    /**
     *  Airing Today Series
     */
    private fun getAiringTodaySeries() {
        viewModelScope.launch {
            try {
                getAiringTodaySeriesUseCase().collect { items ->
                    val airingTodaySeries = items.map {
                        mediaUiMapper.map(it)
                    }
                    _homeUiState.update {
                        it.copy(airingTodaySeries = HomeItem.AiringTodaySeries(airingTodaySeries))
                    }
                }
            } catch (e: Exception) {
                Log.d("", "")
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
                    val seriesItems = list.map(mediaUiMapper::map)
                    _homeUiState.update {
                        it.copy(tvSeriesLists = HomeItem.TVSeriesLists(seriesItems))
                    }
                }
            } catch (e: Exception) {
                Log.d("", "")
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
                    val actorItems = list.map {
                        actorUiMapper.map(it)
                    }
                    _homeUiState.update {
                        it.copy(actors = HomeItem.Actor(actorItems))
                    }
                }
            }  catch (e: Exception) {
                Log.d("", "")
            }
        }

    }


    override fun onClickMovie(movieID: Int) {
        _homeUiEvent.update {
            Event(HomeUiEvent.ClickMovieEvent(movieID))
        }
    }

    override fun onClick(mediaID: Int) {
        TODO("Not yet implemented")
    }


}