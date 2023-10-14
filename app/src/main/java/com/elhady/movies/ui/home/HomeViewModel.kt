package com.elhady.movies.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.home.GetNowPlayingMoviesUseCase
import com.elhady.movies.domain.usecases.home.GetPopularMoviesUseCase
import com.elhady.movies.domain.usecases.home.GetTopRatedMoviesUseCase
import com.elhady.movies.domain.usecases.home.GetTrendingMoviesUseCase
import com.elhady.movies.domain.usecases.home.GetUpcomingMoviesUseCase
import com.elhady.movies.domain.usecases.home.series.GetOnTheAirSeriesUseCase
import com.elhady.movies.ui.adapters.MovieInteractionListener
import com.elhady.movies.ui.home.homeUiState.HomeUiEvent
import com.elhady.movies.ui.home.homeUiState.HomeUiState
import com.elhady.movies.ui.mappers.MediaUiMapper
import com.elhady.movies.ui.mappers.PopularUiMapper
import com.elhady.movies.utilities.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
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
    private val getOnTheAirSeriesUseCase: GetOnTheAirSeriesUseCase
) :
    ViewModel(), MovieInteractionListener {

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
    }

    /**
     *  Movies
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
     *  Series
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
            }catch (e: Exception){
                Log.i("", "")
            }
        }
    }


    override fun onClickMovie(movieID: Int) {
        _homeUiEvent.update {
            Event(HomeUiEvent.ClickMovieEvent(movieID))
        }
    }


}