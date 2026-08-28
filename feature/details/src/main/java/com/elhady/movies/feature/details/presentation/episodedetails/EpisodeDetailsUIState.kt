package com.elhady.movies.feature.details.presentation.episodedetails

import com.elhady.movies.core.ui.base.ErrorUiState
import com.elhady.movies.core.ui.state.PeopleUiState

data class EpisodeDetailsUiState(
    val imageUrl: String = "",
    val episodeName: String = "",
    val episodeRate: Float = 0f,
    val userRate: Float = 0f,
    val episodeOverview: String = "",
    val episodeNumber: Int = 0,
    val voteAverage: Float = 0f,
    val seasonNumber: Int = 0,
    val cast: List<PeopleUiState> = emptyList(),
    val trailerKey: String = "",
    val error: ErrorUiState? = null,
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val isLoggedIn: Boolean = false,
    val isPlayerVisible: Boolean = false
) {
    val isFailure: Boolean
        get() = error != null
}