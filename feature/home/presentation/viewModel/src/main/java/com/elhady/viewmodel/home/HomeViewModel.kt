package com.elhady.viewmodel.home

import com.elhady.base.BaseViewModel
import com.elhady.viewmodel.home.homeUiState.HomeUiEvent
import com.elhady.viewmodel.home.homeUiState.HomeUiState
import com.elhady.viewmodel.home.mappers.PopularActorUiMapper
import com.elhady.viewmodel.home.mappers.TrendingMovieUiMapper
import com.elhady.viewmodel.home.mappers.PopularMovieUiMapper
import com.elhady.usecase.home.movies.GetAdventureMoviesUseCase
import com.elhady.usecase.home.movies.GetMysteryMoviesUseCase
import com.elhady.usecase.home.movies.GetNowPlayingMoviesUseCase
import com.elhady.usecase.home.movies.GetPopularMoviesUseCase
import com.elhady.usecase.home.movies.GetTopRatedMoviesUseCase
import com.elhady.usecase.home.movies.GetTrendingMoviesUseCase
import com.elhady.usecase.home.movies.GetUpcomingMoviesUseCase
import com.elhady.usecase.home.actor.GetPopularActorUseCase
import com.elhady.usecase.home.series.GetAiringTodaySeriesUseCase
import com.elhady.usecase.home.series.GetOnTheAirSeriesUseCase
import com.elhady.usecase.home.series.GetTVSeriesListsUseCase
import com.elhady.usecase.seeAllMedia.ShowMoreType
import com.elhady.viewmodel.home.homeUiState.AdventureMoviesUiState
import com.elhady.viewmodel.home.homeUiState.AiringTodayTVShowsUiState
import com.elhady.viewmodel.home.homeUiState.HomeListener
import com.elhady.viewmodel.home.homeUiState.MysteryMoviesUiState
import com.elhady.viewmodel.home.homeUiState.NowPlayingMoviesUiState
import com.elhady.viewmodel.home.homeUiState.OnTheAirTVShowsUiState
import com.elhady.viewmodel.home.homeUiState.PopularActorUiState
import com.elhady.viewmodel.home.homeUiState.PopularMoviesUiState
import com.elhady.viewmodel.home.homeUiState.TVShowsListsUiState
import com.elhady.viewmodel.home.homeUiState.TopRatedMoviesUiState
import com.elhady.viewmodel.home.homeUiState.TrendingMoviesUiState
import com.elhady.viewmodel.home.homeUiState.UpcomingMoviesUiState
import com.elhady.viewmodel.home.mappers.AdventureMovieUiMapper
import com.elhady.viewmodel.home.mappers.AiringTodayTVShowsUiMapper
import com.elhady.viewmodel.home.mappers.MysteryMovieUiMapper
import com.elhady.viewmodel.home.mappers.NowPlayingMovieUiMapper
import com.elhady.viewmodel.home.mappers.OnTheAirTVShowsUiMapper
import com.elhady.viewmodel.home.mappers.TVShowsListsUiMapper
import com.elhady.viewmodel.home.mappers.TopRatedMovieUiMapper
import com.elhady.viewmodel.home.mappers.UpcomingMovieUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val popularMovieUiMapper: PopularMovieUiMapper,
    private val getTrendingMovieUseCase: GetTrendingMoviesUseCase,
    private val trendingMovieUiMapper: TrendingMovieUiMapper,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val upcomingMovieUiMapper: UpcomingMovieUiMapper,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val nowPlayingMovieUiMapper: NowPlayingMovieUiMapper,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val topRatedMovieUiMapper: TopRatedMovieUiMapper,
    private val getOnTheAirSeriesUseCase: GetOnTheAirSeriesUseCase,
    private val onTheAirTVShowsUiMapper: OnTheAirTVShowsUiMapper,
    private val getAiringTodaySeriesUseCase: GetAiringTodaySeriesUseCase,
    private val airingTodayTVShowsUiMapper: AiringTodayTVShowsUiMapper,
    private val getTVSeriesListsUseCase: GetTVSeriesListsUseCase,
    private val tvShowsListsUiMapper: TVShowsListsUiMapper,
    private val getMysteryMoviesUseCase: GetMysteryMoviesUseCase,
    private val mysteryMovieUiMapper: MysteryMovieUiMapper,
    private val getAdventureMoviesUseCase: GetAdventureMoviesUseCase,
    private val adventureMovieUiMapper: AdventureMovieUiMapper,
    private val getPopularActorUseCase: GetPopularActorUseCase,
    private val popularActorUiMapper: PopularActorUiMapper
) :
    BaseViewModel<HomeUiState, HomeUiEvent>(HomeUiState()), HomeListener {

    init {
        getData()
    }

    fun getData() {
        _state.update { it.copy(isLoading = true, onErrors = emptyList()) }
        getPopularMovies()
        getUpcomingMovies()
        getTrendingMovie()
        getNowPlayingMovies()
        getTopRatedMovies()
        getOnTheAirSeries()
        getAiringTodaySeries()
        getTVSeriesLists()
        getMysteryMovies()
        getAdventureMovies()
        getPopularPersons()

    }

    /**
     *  Popular Movies
     */
    private fun getPopularMovies(){
        tryToExecute(
            call =  {getPopularMoviesUseCase()} ,
            onSuccess = ::onSuccessPopularMovies,
            mapper = popularMovieUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessPopularMovies(items :List<PopularMoviesUiState> ){
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
            mapper = upcomingMovieUiMapper
        )
    }

    private fun onSuccessUpcoming(items: List<UpcomingMoviesUiState>) {
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
            mapper = trendingMovieUiMapper
        )
    }

    private fun onSuccessTrending(items: List<TrendingMoviesUiState>) {
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
            mapper = nowPlayingMovieUiMapper
        )
    }

    private fun onSuccessNowPlaying(items: List<NowPlayingMoviesUiState>) {
        _state.update { it.copy(nowPlayingMovie = items, isLoading = false, onErrors = emptyList()) }
    }

    /**
     *  Top Popular Movies
     */
    private fun getTopRatedMovies() {
        tryToExecute(
            call = { getTopRatedMoviesUseCase() },
            onSuccess = ::onSuccessTopRated,
            mapper = topRatedMovieUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessTopRated(items: List<TopRatedMoviesUiState>) {
        _state.update { it.copy(topRatedMovie = items, isLoading = false, onErrors = emptyList()) }
    }

    /**
     *  Mystery Movies
     */
    private fun getMysteryMovies() {
        tryToExecute(
            call = { getMysteryMoviesUseCase() },
            onSuccess = ::onSuccessMystery,
            mapper = mysteryMovieUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessMystery(items: List<MysteryMoviesUiState>) {
        _state.update { it.copy(mysteryMovies = items, isLoading = false, onErrors = emptyList()) }
    }

    /**
     *  Adventure Movies
     */
    private fun getAdventureMovies() {
        tryToExecute(
            call = { getAdventureMoviesUseCase() },
            onSuccess = ::onSuccessAdventure,
            mapper = adventureMovieUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessAdventure(items: List<AdventureMoviesUiState>) {
        _state.update { it.copy(adventureMovies = items, isLoading = false, onErrors = emptyList()) }
    }

    /**
     *  On The Air Series
     */
    private fun getOnTheAirSeries() {
        tryToExecute(
            call = { getOnTheAirSeriesUseCase() },
            onSuccess = ::onSuccessTheAirSeries,
            mapper = onTheAirTVShowsUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessTheAirSeries(items: List<OnTheAirTVShowsUiState>) {
        _state.update { it.copy(onTheAirTVShows = items, isLoading = false, onErrors = emptyList()) }
    }

    /**
     *  Airing Today Series
     */
    private fun getAiringTodaySeries() {
        tryToExecute(
            call = { getAiringTodaySeriesUseCase() },
            onSuccess = ::onSuccessAiringTodaySeries,
            mapper = airingTodayTVShowsUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessAiringTodaySeries(items: List<AiringTodayTVShowsUiState>) {
        _state.update { it.copy(airingTodayTVShows = items, isLoading = false, onErrors = emptyList()) }
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
            mapper = tvShowsListsUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessTVSeriesLists(items: List<TVShowsListsUiState>) {
        _state.update { it.copy(tvShowsLists = items, isLoading = false, onErrors = emptyList()) }
    }

    /**
     *  Trending Actors
     */
    private fun getPopularPersons() {
        tryToExecute(
            call = { getPopularActorUseCase() },
            onSuccess = ::onSuccessPopularPersons,
            mapper = popularActorUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessPopularPersons(items: List<PopularActorUiState>) {
        _state.update { it.copy(popularPeople = items, isLoading = false, onErrors = emptyList()) }
    }

    private fun onError(error: Throwable){
        val errors = _state.value.onErrors.toMutableList()
        errors.add(error.message.toString())
        _state.update { it.copy(onErrors = errors, isLoading = false) }
    }


    override fun onClickMovieDetails(id: Int) {
        sendEvent(HomeUiEvent.ClickMovieEvent(id))
    }

    override fun onClickTVShowDetails(id: Int) {
        sendEvent(HomeUiEvent.ClickTVShowEvent(id))
    }

    override fun onClickActorDetails(id: Int) {
        sendEvent(HomeUiEvent.ClickActorEvent(id))
    }

    override fun onClickShowMore(type: ShowMoreType) {
        sendEvent(HomeUiEvent.ClickShowMoreEvent(type))
    }

}