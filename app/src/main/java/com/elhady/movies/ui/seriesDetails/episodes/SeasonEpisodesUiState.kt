package com.elhady.movies.ui.seriesDetails.episodes

data class SeasonEpisodesUiState(
    val episodeDate: String = "",
    val episodeDescription: String = "",
    val episodeDuration: Int = 0,
    val episodeId: Int = 0,
    val episodeImageUrl: String = "",
    val episodeName: String = "",
    val episodeNumber: Int = 0,
    val episodeRate: Double = 0.0,
    val episodeTotalReviews: String = ""
)
