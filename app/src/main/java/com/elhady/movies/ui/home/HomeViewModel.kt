package com.elhady.movies.ui.home

import com.elhady.movies.domain.enums.AllMediaType
import com.elhady.movies.domain.enums.SeeAllType
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
import com.elhady.movies.ui.models.ActorUiState
import com.elhady.movies.ui.models.MediaUiState
import com.elhady.movies.ui.models.PopularUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
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
        _state.update { it.copy(isLoading = true, onErrors = emptyList()) }
        getPopular()
        getUpcomingMovies()
        getTrendingMovie()
        getNowPlayingMovies()
        getTopRatedMovies()
        getOnTheAirSeries()
        getAiringTodaySeries()
        getTVSeriesLists()

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
        _state.update { it.copy(popularMovieSlider = items, isLoading = false, onErrors = emptyList()) }
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
        _state.update { it.copy(upcomingMovie = items, isLoading = false, onErrors = emptyList()) }
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
        _state.update { it.copy(trendingMovie = items, isLoading = false, onErrors = emptyList()) }
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
        _state.update { it.copy(nowPlayingMovie = items, isLoading = false, onErrors = emptyList()) }
    }

    /**
     *  Top Popular Movies
     */
    private fun getTopRatedMovies() {
        tryToExecute(
            call = { getTopRatedMoviesUseCase() },
            onSuccess = ::onSuccessTopRated,
            mapper = mediaUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessTopRated(items: List<MediaUiState>) {
        _state.update { it.copy(topRatedMovie = items, isLoading = false, onErrors = emptyList()) }
    }

    /**
     *  Mystery Movies
     */
    private fun getMysteryMovies() {
        tryToExecute(
            call = { getMysteryMoviesUseCase() },
            onSuccess = ::onSuccessMystery,
            mapper = mediaUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessMystery(items: List<MediaUiState>) {
        _state.update { it.copy(mysteryMovies = items, isLoading = false, onErrors = emptyList()) }
    }

    /**
     *  Adventure Movies
     */
    private fun getAdventureMovies() {
        tryToExecute(
            call = { getAdventureMoviesUseCase() },
            onSuccess = ::onSuccessAdventure,
            mapper = mediaUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessAdventure(items: List<MediaUiState>) {
        _state.update { it.copy(adventureMovies = items, isLoading = false, onErrors = emptyList()) }
    }

    /**
     *  On The Air Series
     */
    private fun getOnTheAirSeries() {
        tryToExecute(
            call = { getOnTheAirSeriesUseCase() },
            onSuccess = ::onSuccessTheAirSeries,
            mapper = mediaUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessTheAirSeries(items: List<MediaUiState>) {
        _state.update { it.copy(onTheAirSeries = items, isLoading = false, onErrors = emptyList()) }
    }

    /**
     *  Airing Today Series
     */
    private fun getAiringTodaySeries() {
        tryToExecute(
            call = { getAiringTodaySeriesUseCase() },
            onSuccess = ::onSuccessAiringTodaySeries,
            mapper = mediaUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessAiringTodaySeries(items: List<MediaUiState>) {
        _state.update { it.copy(airingTodaySeries = items, isLoading = false, onErrors = emptyList()) }
    }

    /**
     *  TV Series Lists
     * * Popular
     * * Top Rated
     * * Latest
     */
    private fun getTVSeriesLists() {
        tryToExecute(
            call = { getTVSeriesListsUseCase() },
            onSuccess = ::onSuccessTVSeriesLists,
            mapper = mediaUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessTVSeriesLists(items: List<MediaUiState>) {
        _state.update { it.copy(tvSeriesLists = items, isLoading = false, onErrors = emptyList()) }
    }

    /**
     *  Trending Actors
     */
    private fun getPopularPersons() {
        tryToExecute(
            call = { getTrendingActorsUseCase() },
            onSuccess = ::onSuccessPopularPersons,
            mapper = actorUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessPopularPersons(items: List<ActorUiState>) {
        _state.update { it.copy(popularPeople = items, isLoading = false, onErrors = emptyList()) }
    }

    private fun onError(error: Throwable){
        val errors = _state.value.onErrors.toMutableList()
        errors.add(error.message.toString())
        _state.update { it.copy(onErrors = errors, isLoading = false) }
    }

    override fun onClickMovie(movieID: Int) {
        sendEvent(HomeUiEvent.ClickMovieEvent(movieID))
    }

    override fun onClickSeeAllMovies(mediaType: SeeAllType) {
        sendEvent(HomeUiEvent.ClickSeeAllMoviesEvent(mediaType))
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