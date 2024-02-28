package com.elhady.entities

data class EpisodeEntity(
    val episodeDate: String,
    val episodeDescription: String,
    val episodeDuration: Int,
    val episodeId: Int,
    val episodeImageUrl: String,
    val episodeName: String,
    val episodeNumber: Int,
    val episodeRate: Double,
    val episodeTotalReviews: String
)
