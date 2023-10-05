package com.elhady.movies.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.home.GetNowPlayingMoviesUseCase
import com.elhady.movies.domain.usecases.home.GetPopularMoviesUseCase
import com.elhady.movies.domain.usecases.home.GetTrendingMovieUseCase
import com.elhady.movies.domain.usecases.home.GetUpcomingMoviesUseCase
import com.elhady.movies.ui.adapters.MovieInteractionListener
import com.elhady.movies.ui.home.adapters.HomeInteractionListener
import com.elhady.movies.ui.home.homeUiState.HomeUiState
import com.elhady.movies.ui.mappers.MediaUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val popularUiMapper: PopularUiMapper,
    private val mediaUiMapper: MediaUiMapper,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getTrendingMovieUseCase: GetTrendingMovieUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
) :
    ViewModel(), HomeInteractionListener, MovieInteractionListener {

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    private fun getPopular() {
        viewModelScope.launch {
            try {
                getPopularMoviesUseCase().collect { items ->
                    if (items.isNotEmpty()) {
                        val popularUiState = items.map(popularUiMapper::map)
                        _homeUiState.update {
                            it.copy(popularMovie = HomeItem.Slider(popularUiState))
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
                            it.copy(upcomingMovie = HomeItem.Upcoming(upcomingItems))
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
                            it.copy(trendingMovie = HomeItem.Trending(trendingItems))
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
                            it.copy(nowPlayingMovie = HomeItem.NowPlaying(nowPlayingItems))
                        }
                    }

                }
            } catch (e: Exception) {
                Log.i("", "")
            }
        }
    }


    init {
        getPopular()
        getUpcomingMovies()
        getTrendingMovie()
        getNowPlayingMovies()
    }

    override fun onClickMovie(name: String) {
        TODO("Not yet implemented")
    }

    override fun onClick(movieID: Int) {
        TODO("Not yet implemented")
    }


}