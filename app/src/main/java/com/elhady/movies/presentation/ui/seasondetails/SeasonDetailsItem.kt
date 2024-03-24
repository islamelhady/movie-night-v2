package com.elhady.movies.presentation.ui.seasondetails

import com.elhady.movies.presentation.viewmodel.common.model.EpisodeHorizontalUIState

sealed class SeasonDetailsItem(val type: SeasonDetailsType){
    data class OverviewItem(val overview: String, val isEmptyEpisodes: Boolean): SeasonDetailsItem(SeasonDetailsType.OVERVIEW)

    data class EpisodeItem(val episodeHorizontalUIState: EpisodeHorizontalUIState): SeasonDetailsItem(SeasonDetailsType.EPISODE)
}

enum class SeasonDetailsType{
    OVERVIEW,
    EPISODE
}
