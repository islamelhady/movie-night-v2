package com.elhady.movies.ui.allMedia

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.map
import com.elhady.movies.domain.enums.SeeAllType
import com.elhady.movies.domain.usecases.seeAllMedia.CheckMediaTypeUseCase
import com.elhady.movies.domain.usecases.seeAllMedia.GetAllMediaByTypeUseCase
import com.elhady.movies.domain.usecases.seeAllMedia.GetSeeAllMovieAdventureUseCase
import com.elhady.movies.domain.usecases.seeAllMedia.GetSeeAllMovieMysteryUseCase
import com.elhady.movies.domain.usecases.seeAllMedia.GetSeeAllMovieNowPlayingUseCase
import com.elhady.movies.domain.usecases.seeAllMedia.GetSeeAllMovieTopRatedUseCase
import com.elhady.movies.domain.usecases.seeAllMedia.GetSeeAllMovieTrendingUseCase
import com.elhady.movies.domain.usecases.seeAllMedia.GetSeeAllSeriesOnTheAirUseCase
import com.elhady.movies.domain.usecases.seeAllMedia.GetSeeAllSeriesPopularUseCase
import com.elhady.movies.domain.usecases.seeAllMedia.GetSeeAllSeriesTopRatedUseCase
import com.elhady.movies.domain.usecases.seeAllMedia.GetSeeAllUpcomingMovieUseCase
import com.elhady.movies.ui.adapter.MediaInteractionListener
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.ui.mappers.MediaUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllMediaViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val getAllMediaByTypeUseCase: GetAllMediaByTypeUseCase,
    private val getSeeAllUpcomingMovieUseCase: GetSeeAllUpcomingMovieUseCase,
    private val getSeeAllMovieTrendingUseCase: GetSeeAllMovieTrendingUseCase,
    private val getSeeAllMovieNowPlayingUseCase: GetSeeAllMovieNowPlayingUseCase,
    private val getSeeAllMovieMysteryUseCase: GetSeeAllMovieMysteryUseCase,
    private val getSeeAllMovieAdventureUseCase: GetSeeAllMovieAdventureUseCase,
    private val getSeeAllSeriesPopularUseCase: GetSeeAllSeriesPopularUseCase,
    private val getSeeAllMovieTopRatedUseCase: GetSeeAllMovieTopRatedUseCase,
    private val getSeeAllSeriesTopRatedUseCase: GetSeeAllSeriesTopRatedUseCase,
    private val getSeeAllSeriesOnTheAirUseCase: GetSeeAllSeriesOnTheAirUseCase,
    private val checkMediaTypeUseCase: CheckMediaTypeUseCase,
    private val mediaUiMapper: MediaUiMapper
) : BaseViewModel<AllMediaUiState, AllMediaUiEvent>(AllMediaUiState()), MediaInteractionListener {

    private val args = AllMediaFragmentArgs.fromSavedStateHandle(savedStateHandle)

    init {
        _state.update { it.copy(isLoading = true) }
        getData()
    }

    override fun getData() {
        when(_state.value.mediaType){
            SeeAllType.TOP_RATED_TV -> getTopRatedTvShowSeeAll()
            SeeAllType.POPULAR_TV -> getPopularTvShowSeeAll()
            SeeAllType.LATEST_TV -> TODO()
            SeeAllType.ON_THE_AIR_TV -> getOnTheAirTvShowSeeAll()
            SeeAllType.UPCOMING_MOVIE -> getUpcomingMovieSeeAll()
            SeeAllType.TRENDING_MOVIE -> getTrendingMovieSeeAll()
            SeeAllType.NOW_PLAYING_MOVIE -> getNowPlayingMovieSeeAll()
            SeeAllType.TOP_RATED_MOVIE -> getTopRatedMovieSeeAll()
            SeeAllType.MYSTERY_MOVIE -> getMysteryMovieSeeAll()
            SeeAllType.ADVENTURE_MOVIE -> getAdventureMovieSeeAll()
            SeeAllType.ACTOR_MOVIES -> TODO()
            SeeAllType.POPULAR_PEOPLE -> TODO()
        }
        getAllMedia()
    }

    private fun getUpcomingMovieSeeAll() {
        viewModelScope.launch {
            val items = getSeeAllUpcomingMovieUseCase().map { pagingData ->
                pagingData.map(mediaUiMapper::map)
            }
            _state.update { it.copy(seeAllUpcomingMovies = items, isLoading = false, mediaType = SeeAllType.UPCOMING_MOVIE) }
        }
    }

    private fun getTrendingMovieSeeAll() {
        viewModelScope.launch {
            val items = getSeeAllMovieTrendingUseCase().map { pagingData ->
                pagingData.map(mediaUiMapper::map)
            }
            _state.update { it.copy(seeAllTrendingMovies = items, isLoading = false, mediaType = SeeAllType.TRENDING_MOVIE) }
        }
    }

    private fun getNowPlayingMovieSeeAll() {
        viewModelScope.launch {
            val items = getSeeAllMovieNowPlayingUseCase().map { pagingData ->
                pagingData.map(mediaUiMapper::map)
            }
            _state.update { it.copy(seeAllNowPlayingMovies = items, isLoading = false, mediaType = SeeAllType.NOW_PLAYING_MOVIE) }
        }
    }

    private fun getMysteryMovieSeeAll() {
        viewModelScope.launch {
            val items = getSeeAllMovieMysteryUseCase().map { pagingData ->
                pagingData.map(mediaUiMapper::map)
            }
            _state.update { it.copy(seeAllNowMysteryMovies = items, isLoading = false, mediaType = SeeAllType.MYSTERY_MOVIE) }
        }
    }

    private fun getAdventureMovieSeeAll() {
        viewModelScope.launch {
            val items = getSeeAllMovieAdventureUseCase().map { pagingData ->
                pagingData.map(mediaUiMapper::map)
            }
            _state.update { it.copy(seeAllNowAdventureMovies = items, isLoading = false, mediaType = SeeAllType.ADVENTURE_MOVIE) }
        }
    }

    private fun getPopularTvShowSeeAll() {
        viewModelScope.launch {
            val items = getSeeAllSeriesPopularUseCase().map { pagingData ->
                pagingData.map(mediaUiMapper::map)
            }
            _state.update { it.copy(seeAllPopularTvShow = items, isLoading = false, mediaType = SeeAllType.POPULAR_TV) }
        }
    }

    private fun getTopRatedMovieSeeAll() {
        viewModelScope.launch {
            val items = getSeeAllMovieTopRatedUseCase().map { pagingData ->
                pagingData.map(mediaUiMapper::map)
            }
            _state.update { it.copy(seeAllTopRatedMovies = items, isLoading = false, mediaType = SeeAllType.TOP_RATED_MOVIE) }
        }
    }

    private fun getTopRatedTvShowSeeAll() {
        viewModelScope.launch {
            val items = getSeeAllSeriesTopRatedUseCase().map { pagingData ->
                pagingData.map(mediaUiMapper::map)
            }
            _state.update { it.copy(seeAllPopularTvShow = items, isLoading = false, mediaType = SeeAllType.TOP_RATED_TV) }
        }
    }

    private fun getOnTheAirTvShowSeeAll() {
        viewModelScope.launch {
            val items = getSeeAllSeriesOnTheAirUseCase().map { pagingData ->
                pagingData.map(mediaUiMapper::map)
            }
            _state.update { it.copy(seeAllOnTheAirTvShow = items, isLoading = false, mediaType = SeeAllType.ON_THE_AIR_TV) }
        }
    }
    private fun getAllMedia() {
        viewModelScope.launch {
            val items =
                getAllMediaByTypeUseCase(type = args.type, actionId = args.id).map { pagingData ->
                    pagingData.map { mediaUiMapper.map(it) }
                }
            _state.update {
                it.copy(allMedia = items, isLoading = false)
            }
        }
    }


    fun setErrorUiState(combinedLoadStates: CombinedLoadStates) {
        when (combinedLoadStates.refresh) {
            is LoadState.NotLoading -> {
                _state.update {
                    it.copy(isLoading = false, error = emptyList())
                }
            }

            is LoadState.Loading -> {
                _state.update {
                    it.copy(isLoading = true, error = emptyList())
                }
            }

            is LoadState.Error -> {
                _state.update {
                    it.copy(isLoading = false, error = listOf(Error(404, "Not found.")))
                }
            }
        }

    }

    override fun onClickMedia(mediaId: Int) {
        if (checkMediaTypeUseCase(args.type)) {
            sendEvent(AllMediaUiEvent.ClickSeriesEvent(mediaId))
        } else {
            sendEvent(AllMediaUiEvent.ClickMovieEvent(mediaId))
        }
    }


}