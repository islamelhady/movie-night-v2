package com.elhady.movies.ui.tvShowDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.tvShowDetails.GetTVShowDetailsUseCase
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.ui.home.adapters.ActorInteractionListener
import com.elhady.movies.ui.mappers.ActorUiMapper
import com.elhady.movies.ui.movieDetails.DetailsInteractionListener
import com.elhady.movies.ui.tvShowDetails.tvShowUiMapper.TvShowDetailsUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowDetailsViewModel @Inject constructor(
    state: SavedStateHandle,
    private val getTVShowDetailsUseCase: GetTVShowDetailsUseCase,
    private val tvShowDetailsUiMapper: TvShowDetailsUiMapper,
    private val actorUiMapper: ActorUiMapper
) : BaseViewModel(), DetailsInteractionListener, ActorInteractionListener{

    private val args = TvShowDetailsFragmentArgs.fromSavedStateHandle(state)

    private val _seriesUiState = MutableStateFlow(TVShowDetailsUiState())
    val seriesUiState = _seriesUiState.asStateFlow()

    init {
        getData()
    }

    override fun getData() {
        getTVShowDetails(args.tvShowId)
        getSeriesCast(args.tvShowId)
    }


    private fun getTVShowDetails(tvShowId: Int) {
        viewModelScope.launch {
            val result = tvShowDetailsUiMapper.map(getTVShowDetailsUseCase.getTvShowDetails(tvShowId))
            _seriesUiState.update {
                it.copy(
                    seriesDetailsResult = result
                )
            }
            onAddMovieDetailsItemOfNestedView(SeriesItems.Header(_seriesUiState.value.seriesDetailsResult))
        }
    }

    private fun getSeriesCast(tvShowId: Int){
        viewModelScope.launch {
            val result = getTVShowDetailsUseCase.getSeriesCast(tvShowId).map {
                actorUiMapper.map(it)
            }
            _seriesUiState.update {
                it.copy(seriesCastResult = result)
            }
            onAddMovieDetailsItemOfNestedView(SeriesItems.Cast(_seriesUiState.value.seriesCastResult))
        }
    }

    private fun onAddMovieDetailsItemOfNestedView(items: SeriesItems) {
        val itemsList = _seriesUiState.value.seriesItems.toMutableList()
        itemsList.add(items)
        _seriesUiState.update { it.copy(seriesItems = itemsList.toList()) }
    }

    override fun onClickBackButton() {
        TODO("Not yet implemented")
    }

    override fun onClickPlayTrailer() {
        TODO("Not yet implemented")
    }

    override fun onclickViewReviews() {
        TODO("Not yet implemented")
    }

    override fun onClickActor(actorID: Int) {
        TODO("Not yet implemented")
    }
}