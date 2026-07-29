package com.elhady.movies.core.domain.model.tvshow

data class EpisodeDetails(
    val id: Int,
    val imageUrl: String,
    val episodeName: String,
    val seasonNumber: Int,
    val episodeNumber: Int,
    val overview: String,
    val voteAverage: Float,
    val productionCode: String,
    val episodeRate: Float
)
