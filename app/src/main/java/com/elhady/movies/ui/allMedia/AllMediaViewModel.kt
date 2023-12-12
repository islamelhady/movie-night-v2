package com.elhady.movies.ui.allMedia

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.map
import com.elhady.movies.domain.enums.SeeAllType
import com.elhady.movies.domain.usecases.seeAllMedia.CheckMediaTypeUseCase
import com.elhady.movies.domain.usecases.seeAllMedia.GetAllMediaByTypeUseCase
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
            SeeAllType.TOP_RATED_TV -> TODO()
            SeeAllType.POPULAR_TV -> TODO()
            SeeAllType.LATEST_TV -> TODO()
            SeeAllType.ON_THE_AIR_TV -> TODO()
            SeeAllType.UPCOMING_MOVIE -> getUpcomingMovie()
            SeeAllType.TRENDING_MOVIE -> TODO()
            SeeAllType.NOW_PLAYING_MOVIE -> TODO()
            SeeAllType.TOP_RATED_MOVIE -> TODO()
            SeeAllType.MYSTERY_MOVIE -> TODO()
            SeeAllType.ADVENTURE_MOVIE -> TODO()
            SeeAllType.ACTOR_MOVIES -> TODO()
            SeeAllType.POPULAR_PEOPLE -> TODO()
        }
        getAllMedia()
    }

    private fun getUpcomingMovie() {
        TODO("Not yet implemented")
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