package com.elhady.movies.data.remote.response.series


import com.elhady.movies.data.remote.response.SpokenLanguageDto
import com.elhady.movies.data.remote.response.genre.GenreDto
import com.google.gson.annotations.SerializedName
import java.util.Date

data class SeriesDetailsDto(
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("created_by")
    val createdBy: List<CreatedByDto?>?,
    @SerializedName("episode_run_time")
    val episodeRunTime: List<Int?>?,
    @SerializedName("first_air_date")
    val firstAirDate: Date?,
    @SerializedName("genres")
    val genres: List<GenreDto?>?,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("in_production")
    val inProduction: Boolean?,
    @SerializedName("languages")
    val languages: List<String?>?,
    @SerializedName("last_air_date")
    val lastAirDate: String?,
    @SerializedName("last_episode_to_air")
    val lastEpisodeToAir: LastEpisodeToAirDto?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("networks")
    val networks: List<NetworkDto?>?,
    @SerializedName("next_episode_to_air")
    val nextEpisodeToAir: Any?,
    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int?,
    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int?,
    @SerializedName("origin_country")
    val originCountry: List<String?>?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_name")
    val originalName: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompanyDto?>?,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountryDto?>?,
    @SerializedName("seasons")
    val seasons: List<SeasonDto>?,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageDto?>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("tagline")
    val tagline: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
)