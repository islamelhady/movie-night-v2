package com.elhady.movies.feature.details.presentation.episodedetails

import com.elhady.movies.core.ui.state.PeopleUiState

data class EpisodeDetailsUiState(
    val imageUrl: String = "",
    val episodeName: String = "",
    val episodeRate: Float = 0.0F,
    val userRate: Float = 0f,
    val episodeOverview: String = "",
    val episodeNumber: Int = 0,
    val voteAverage: Float = 0f,
    val seasonNumber: Int = 0,
    val refreshing: Boolean = false,
    val cast: List<PeopleUiState> = emptyList(),
    val trailerKey: String = "",
    val onErrors: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false
) {
    val isFailure: Boolean
        get() =
            onErrors.isNotEmpty()
}
