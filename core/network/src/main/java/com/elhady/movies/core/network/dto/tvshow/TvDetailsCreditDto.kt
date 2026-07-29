package com.elhady.movies.core.network.dto.tvshow


import com.google.gson.annotations.SerializedName

data class TvDetailsCreditDto(
    @SerializedName("cast")
    val cast: List<CastDto?>?,
    @SerializedName("id")
    val tvShowId: Int?,
) {
    data class CastDto(
        @SerializedName("adult")
        val adult: Boolean?,
        @SerializedName("gender")
        val gender: Int?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("known_for_department")
        val knownForDepartment: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("original_name")
        val originalName: String?,
        @SerializedName("popularity")
        val popularity: Double?,
        @SerializedName("profile_path")
        val profilePath: String?,
        @SerializedName("roles")
        val roles: List<Role?>?,
        @SerializedName("total_episode_count")
        val totalEpisodeCount: Int?,
        @SerializedName("order")
        val order: Int?
    )

    data class Role(
        @SerializedName("credit_id")
        val creditId: String?,
        @SerializedName("character")
        val character: String?,
        @SerializedName("episode_count")
        val episodeCount: Int?
    )
}
