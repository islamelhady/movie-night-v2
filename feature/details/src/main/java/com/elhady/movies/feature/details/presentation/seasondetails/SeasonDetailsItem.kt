package com.elhady.movies.feature.details.presentation.seasondetails

import com.elhady.movies.feature.details.presentation.episodedetails.EpisodeHorizontalUiState

sealed class SeasonDetailsItem(val type: SeasonDetailsType){
    data class OverviewItem(val overview: String, val isEmptyEpisodes: Boolean): SeasonDetailsItem(SeasonDetailsType.OVERVIEW)

    data class EpisodeItem(val episodeHorizontalUiState: EpisodeHorizontalUiState): SeasonDetailsItem(SeasonDetailsType.EPISODE)
}

enum class SeasonDetailsType{
    OVERVIEW,
    EPISODE
}
