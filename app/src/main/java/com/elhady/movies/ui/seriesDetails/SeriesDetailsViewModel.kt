package com.elhady.movies.ui.seriesDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.seriesDetails.GetSeriesDetailsUseCase
import com.elhady.movies.ui.adapter.MediaInteractionListener
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.ui.home.adapters.ActorInteractionListener
import com.elhady.movies.ui.mappers.ActorUiMapper
import com.elhady.movies.ui.mappers.MediaUiMapper
import com.elhady.movies.ui.mappers.ReviewUiMapper
import com.elhady.movies.ui.movieDetails.DetailsInteractionListener
import com.elhady.movies.ui.seriesDetails.seriesUiMapper.SeasonUiMapper
import com.elhady.movies.ui.seriesDetails.seriesUiMapper.SeriesDetailsUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesDetailsViewModel @Inject constructor(
    state: SavedStateHandle,
    private val getSeriesDetailsUseCase: GetSeriesDetailsUseCase,
    private val seriesDetailsUiMapper: SeriesDetailsUiMapper,
    private val actorUiMapper: ActorUiMapper,
    private val mediaUiMapper: MediaUiMapper,
    private val seasonUiMapper: SeasonUiMapper,
    private val reviewUiMapper: ReviewUiMapper
) : BaseViewModel(), DetailsInteractionListener, ActorInteractionListener, MediaInteractionListener, SeasonInteractionListener{

    private val args = SeriesDetailsFragmentArgs.fromSavedStateHandle(state)

    private val _seriesUiState = MutableStateFlow(SeriesDetailsUiState())
    val seriesUiState = _seriesUiState.asStateFlow()

    init {
        getData()
    }

    override fun getData() {
        getTVShowDetails(args.seriesId)
        getSeriesCast(args.seriesId)
        getSimilarSeries(args.seriesId)
        getSeasonSeries(args.seriesId)
        getSeriesReview(args.seriesId)
    }


    private fun getTVShowDetails(tvShowId: Int) {
        viewModelScope.launch {
            val result = seriesDetailsUiMapper.map(getSeriesDetailsUseCase.getSeriesDetails(tvShowId))
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
            val result = getSeriesDetailsUseCase.getSeriesCast(tvShowId).map {
                actorUiMapper.map(it)
            }
            _seriesUiState.update {
                it.copy(seriesCastResult = result)
            }
            onAddMovieDetailsItemOfNestedView(SeriesItems.Cast(_seriesUiState.value.seriesCastResult))
        }
    }

    private fun getSimilarSeries(seriesId: Int){
        viewModelScope.launch {
            val result = getSeriesDetailsUseCase.getSimilarSeries(seriesId).map {
                mediaUiMapper.map(it)
            }
            _seriesUiState.update {
                it.copy(seriesSimilarResult = result)
            }
            onAddMovieDetailsItemOfNestedView(SeriesItems.Similar(_seriesUiState.value.seriesSimilarResult))
        }
    }

    private fun getSeasonSeries(seriesId: Int){
        viewModelScope.launch {
            val result = getSeriesDetailsUseCase.getSeasons(seriesId).map {
                seasonUiMapper.map(it)
            }
            _seriesUiState.update {
                it.copy(seriesSeasonsResult = result )
            }
            onAddMovieDetailsItemOfNestedView(SeriesItems.Season(_seriesUiState.value.seriesSeasonsResult))
        }

    }

    private fun getSeriesReview(seriesId: Int){
        viewModelScope.launch {
            val result = getSeriesDetailsUseCase.getSeriesReview(seriesId)
            _seriesUiState.update {
                it.copy(seriesReviewResult = result.reviews.map(reviewUiMapper::map))
            }
            if (result.reviews.isNotEmpty()){
                _seriesUiState.value.seriesReviewResult.forEach {
                    onAddMovieDetailsItemOfNestedView(SeriesItems.Review(it))
                }
                onAddMovieDetailsItemOfNestedView(SeriesItems.ReviewText)
            }
        }

    }

    private fun setReview(seeAllReviews: Boolean){
        _seriesUiState.value.seriesReviewResult.forEach {
            onAddMovieDetailsItemOfNestedView(SeriesItems.Review(it))
        }
        if (seeAllReviews){
            onAddMovieDetailsItemOfNestedView(SeriesItems.ReviewText)
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

    override fun onClickMedia(mediaId: Int) {
        TODO("Not yet implemented")
    }

    override fun onClickSeason(seasonNumber: Int) {
        TODO("Not yet implemented")
    }
}