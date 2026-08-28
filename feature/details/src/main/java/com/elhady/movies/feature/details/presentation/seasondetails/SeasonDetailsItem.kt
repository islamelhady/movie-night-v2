package com.elhady.movies.feature.details.presentation.seasondetails

import com.elhady.movies.feature.details.presentation.episodedetails.EpisodeHorizontalUiState

sealed interface SeasonDetailsItem {
    data class Overview(val overview: String, val isEmptyEpisodes: Boolean) : SeasonDetailsItem

    data class Episode(val episode: EpisodeHorizontalUiState) : SeasonDetailsItem
}
