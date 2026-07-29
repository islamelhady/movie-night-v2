package com.elhady.movies.core.network.api

import com.elhady.movies.core.network.dto.tvshow.RateRequest
import com.elhady.movies.core.network.dto.tvshow.RatingEpisodeDetailsRequest
import com.elhady.movies.core.network.dto.common.DataWrapperResponse
import com.elhady.movies.core.network.dto.common.StatusResponse
import com.elhady.movies.core.network.dto.tvshow.TVShowsDto
import com.elhady.movies.core.network.dto.common.TvReviewDto
import com.elhady.movies.core.network.dto.common.YoutubeVideoDetailsDto
import com.elhady.movies.core.network.dto.tvshow.EpisodeDetailsCastDto
import com.elhady.movies.core.network.dto.tvshow.EpisodeDetailsDto
import com.elhady.movies.core.network.dto.tvshow.RatingEpisodeDetailsDto
import com.elhady.movies.core.network.dto.tvshow.SeasonDetailsDto
import com.elhady.movies.core.network.dto.tvshow.TvDetailsCreditDto
import com.elhady.movies.core.network.dto.tvshow.TvDetailsDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowApiService {

    @GET("tv/airing_today")
    suspend fun getAiringTodayTVShows(@Query("page") page: Int = 1): Response<DataWrapperResponse<TVShowsDto>>

    @GET("tv/top_rated")
    suspend fun getTopRatedTVShows(@Query("page") page: Int = 1): Response<DataWrapperResponse<TVShowsDto>>

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTVShows(@Query("page") page: Int = 1): Response<DataWrapperResponse<TVShowsDto>>

    @GET("tv/popular")
    suspend fun getPopularTVShows(@Query("page") page: Int = 1): Response<DataWrapperResponse<TVShowsDto>>

    @GET("tv/{tv_id}/videos")
    suspend fun getTrailerVideoForTvShow(
        @Path("tv_id") tvShowId: Int
    ): Response<DataWrapperResponse<YoutubeVideoDetailsDto>>

    @GET("tv/{series_id}/season/{season_number}/episode/{episode_number}/videos")
    suspend fun getEpisodeVideos(
        @Path("series_id") seriesId: Int,
        @Path("season_number") seasonNumber: Int,
        @Path("episode_number") episodeNumber: Int
    ): Response<DataWrapperResponse<YoutubeVideoDetailsDto>>

    @GET("tv/{series_id}/season/{season_number}")
    suspend fun getSeasonDetails(
        @Path("series_id") seriesId: Int,
        @Path("season_number") seasonNumber: Int
    ): Response<SeasonDetailsDto>

    @GET("tv/{tv_id}")
    suspend fun getTvDetails(
        @Path("tv_id") tvShowId: Int
    ): Response<TvDetailsDto>

    @GET("tv/{tv_id}/aggregate_credits")
    suspend fun getTvDetailsCredit(
        @Path("tv_id") tvShowId: Int
    ): Response<TvDetailsCreditDto>

    @POST("tv/{tv_id}/rating?")
    suspend fun rateTvShow(
        @Body rateRequest: RateRequest, @Path("tv_id") tvShowId: Int,
    ): Response<StatusResponse>

    @GET("tv/{tv_id}/reviews")
    suspend fun getTvShowReviews(
        @Path("tv_id") tvShowId: Int
    ): Response<DataWrapperResponse<TvReviewDto>>

    @GET("tv/{tv_id}/recommendations")
    suspend fun getTvShowRecommendations(
        @Path("tv_id") tvShowId: Int
    ): Response<DataWrapperResponse<TVShowsDto>>

    @GET("tv/{series_id}/season/{season_number}/episode/{episode_number}")
    suspend fun getEpisodeDetails(
        @Path("series_id") seriesId: Int,
        @Path("season_number") seasonNumber: Int,
        @Path("episode_number") episodeNumber: Int
    ): Response<EpisodeDetailsDto>

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("tv/{series_id}/season/{season_number}/episode/{episode_number}/rating")
    suspend fun postEpisodeRating(
        @Body rate: RatingEpisodeDetailsRequest,
        @Path("series_id") seriesId: Int,
        @Path("season_number") seasonNumber: Int,
        @Path("episode_number") episodeNumber: Int
    ): Response<RatingEpisodeDetailsDto>

    @GET("tv/{series_id}/season/{season_number}/episode/{episode_number}/credits")
    suspend fun getEpisodeCast(
        @Path("series_id") seriesId: Int,
        @Path("season_number") seasonNumber: Int,
        @Path("episode_number") episodeNumber: Int
    ): Response<EpisodeDetailsCastDto>
}
