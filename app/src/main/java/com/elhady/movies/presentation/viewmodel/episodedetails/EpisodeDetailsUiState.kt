package com.elhady.movies.presentation.viewmodel.episodedetails

import com.elhady.movies.presentation.viewmodel.common.model.PeopleUIState

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
    val cast: List<PeopleUIState> = emptyList(),
    val trailerKey: String = "",
    val onErrors: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false
) {
    val isFailure: Boolean
        get() =
            onErrors.isNotEmpty()
}