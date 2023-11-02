package com.elhady.movies.ui.seriesDetails

import com.elhady.movies.ui.models.ActorUiState

sealed class SeriesItems(val priority: Int){
    data class Header(val data: SeriesDetailsResultUiState): SeriesItems(0)
    data class Cast(val data: List<ActorUiState>): SeriesItems(1)
}
