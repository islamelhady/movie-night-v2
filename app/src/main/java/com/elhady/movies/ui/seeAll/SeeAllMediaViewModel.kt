package com.elhady.movies.ui.seeAll

import androidx.lifecycle.SavedStateHandle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.map
import com.elhady.movies.domain.models.Media
import com.elhady.movies.domain.usecases.seeAllMedia.CheckMediaTypeUseCase
import com.elhady.movies.domain.usecases.seeAllMedia.GetAllMediaByTypeUseCase
import com.elhady.movies.ui.adapter.MediaInteractionListener
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.ui.mappers.MediaUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SeeAllMediaViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val getAllMediaByTypeUseCase: GetAllMediaByTypeUseCase,
    private val checkMediaTypeUseCase: CheckMediaTypeUseCase,
    private val mediaUiMapper: MediaUiMapper
) : BaseViewModel<SeeAllMediaUiState, SeeAllMediaUiEvent>(SeeAllMediaUiState()), MediaInteractionListener {

    val args = SeeAllMediaFragmentArgs.fromSavedStateHandle(savedStateHandle)

    init {
        getData()
    }

    override fun getData() {
        _state.update { it.copy(isLoading = true, onErrors = emptyList())}
        tryToExecute(
            call = { getAllMediaByTypeUseCase(type = args.type, actionId = args.id) },
            onSuccess = ::onSuccessMedia,
            onError = ::onError
        )
    }

    private fun onSuccessMedia(media: Flow<PagingData<Media>>) {
        _state.update {
            it.copy(
                allMedia = media.map { pagingData ->
                    pagingData.map(mediaUiMapper::map)
                },
                isLoading = false, onErrors = emptyList()
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
        if (checkMediaTypeUseCase(args.type)) {
            sendEvent(SeeAllMediaUiEvent.ClickSeriesEvent(mediaId))
        } else {
            sendEvent(SeeAllMediaUiEvent.ClickMovieEvent(mediaId))
        }
    }


}