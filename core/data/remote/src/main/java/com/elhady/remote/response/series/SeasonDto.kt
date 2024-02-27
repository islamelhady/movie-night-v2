package com.elhady.remote.response.series

import com.elhady.remote.response.episode.EpisodeDto
import com.google.gson.annotations.SerializedName

data class SeasonDto(
    @SerializedName("air_date")
    val airDate: String?,
    @SerializedName("episode_count")
    val episodeCount: Int?,
    @SerializedName("episodes")
    val episodes: List<EpisodeDto>?,
    @SerializedName("_id")
    val idString: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("season_number")
    val seasonNumber: Int?,
    @SerializedName("vote_average")
    val voteAverage: Double?
)