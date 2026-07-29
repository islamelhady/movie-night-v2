package com.elhady.movies.core.domain.repository

import androidx.paging.Pager
import com.elhady.movies.core.domain.model.tvshow.EpisodeDetails
import com.elhady.movies.core.domain.model.people.People
import com.elhady.movies.core.domain.model.tvshow.RatingEpisodeDetailsStatus
import com.elhady.movies.core.domain.model.tvshow.Season
import com.elhady.movies.core.domain.model.common.Status
import com.elhady.movies.core.domain.model.tvshow.TvShows
import com.elhady.movies.core.domain.model.tvshow.TvShow
import com.elhady.movies.core.domain.model.common.YoutubeVideoDetails
import com.elhady.movies.core.domain.model.account.MyRatedTvShow
import com.elhady.movies.core.domain.model.common.Review
import com.elhady.movies.core.domain.model.tvshow.SeasonDetails
import com.elhady.movies.core.domain.model.tvshow.TvDetailsInfo

interface TvShowRepository {
    suspend fun refreshTvShows()
    suspend fun getTvShowsFromDatabase(): List<TvShows>
    suspend fun refreshAiringTodayTvShows()
    suspend fun getAiringTodayTVShowsFromDatabase(): List<TvShows>
    suspend fun getAiringTodayTVShowsFromRemote(): List<TvShows>
    suspend fun getAiringTodayTVShowsPager(): Pager<Int, TvShows>
    suspend fun getTopRatedTVShowsPager(): Pager<Int, TvShows>
    suspend fun getPopularTVShowsPager(): Pager<Int, TvShows>
    suspend fun getOnTheAirTVShowsPager(): Pager<Int, TvShows>

    suspend fun getSeasonDetails(seriesId: Int, seasonId: Int): SeasonDetails

    suspend fun getTvDetailsInfo(tvShowID: Int): TvDetailsInfo
    suspend fun getTvDetailsSeasons(tvShowID: Int): List<Season>
    suspend fun getTvDetailsCredit(tvShowID: Int): List<People>
    suspend fun rateTvShow(rate: Double, tvShowID: Int): Status
    suspend fun getRateTvShow(): List<MyRatedTvShow>

    suspend fun getTvShowReviews(tvShowID: Int): List<Review>
    suspend fun getTvShowRecommendations(tvShowID: Int): List<TvShow>
    suspend fun getTrailerVideoForTvShow(tvShowID: Int): YoutubeVideoDetails

    suspend fun getVideoEpisodeDetails(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): YoutubeVideoDetails

    suspend fun getCastForEpisode(
        id: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): List<People>

    suspend fun getEpisodeDetails(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): EpisodeDetails

    suspend fun setRatingForEpisode(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int,
        value: Float
    ): RatingEpisodeDetailsStatus

    suspend fun getRatedTvShows(): Pager<Int, MyRatedTvShow>

    suspend fun getTvShowsByPerson(personId: Int): List<TvShow>
}
