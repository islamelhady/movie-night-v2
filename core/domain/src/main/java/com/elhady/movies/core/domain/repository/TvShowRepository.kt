package com.elhady.movies.core.domain.repository

import androidx.paging.Pager
import com.elhady.movies.core.domain.model.EpisodeDetailsEntity
import com.elhady.movies.core.domain.model.PeopleEntity
import com.elhady.movies.core.domain.model.RatingEpisodeDetailsStatusEntity
import com.elhady.movies.core.domain.model.SeasonEntity
import com.elhady.movies.core.domain.model.StatusEntity
import com.elhady.movies.core.domain.model.TVShowsEntity
import com.elhady.movies.core.domain.model.TvShowEntity
import com.elhady.movies.core.domain.model.YoutubeVideoDetailsEntity
import com.elhady.movies.core.domain.model.myrated.MyRatedTvShowEntity
import com.elhady.movies.core.domain.model.ReviewEntity
import com.elhady.movies.core.domain.model.seasondetails.SeasonDetailsEntity
import com.elhady.movies.core.domain.model.tvdetails.TvDetailsInfoEntity

interface TvShowRepository {
    suspend fun refreshTvShows()
    suspend fun getTvShowsFromDatabase(): List<TVShowsEntity>
    suspend fun refreshAiringTodayTvShows()
    suspend fun getAiringTodayTvShowsFromDatabase(): List<TVShowsEntity>
    suspend fun getAiringTodayTVShowsFromRemote(): List<TVShowsEntity>
    suspend fun getAiringTodayTVShowsPager(): Pager<Int, TVShowsEntity>
    suspend fun getTopRatedTVShowsPager(): Pager<Int, TVShowsEntity>
    suspend fun getPopularTVShowsPager(): Pager<Int, TVShowsEntity>
    suspend fun getOnTheAirTVShowsPager(): Pager<Int, TVShowsEntity>

    suspend fun getSeasonDetails(seriesId: Int, seasonId: Int): SeasonDetailsEntity

    suspend fun getTvDetailsInfo(tvShowID: Int): TvDetailsInfoEntity
    suspend fun getTvDetailsSeasons(tvShowID: Int): List<SeasonEntity>
    suspend fun getTvDetailsCredit(tvShowID: Int): List<PeopleEntity>
    suspend fun rateTvShow(rate: Double, tvShowID: Int): StatusEntity
    suspend fun getRateTvShow(): List<MyRatedTvShowEntity>

    suspend fun getTvShowReviews(tvShowID: Int): List<ReviewEntity>
    suspend fun getTvShowRecommendations(tvShowID: Int): List<TvShowEntity>
    suspend fun getTrailerVideoForTvShow(tvShowID: Int): YoutubeVideoDetailsEntity

    suspend fun getVideoEpisodeDetails(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): YoutubeVideoDetailsEntity

    suspend fun getCastForEpisode(
        id: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): List<PeopleEntity>

    suspend fun getEpisodeDetails(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): EpisodeDetailsEntity

    suspend fun setRatingForEpisode(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int,
        value: Float
    ): RatingEpisodeDetailsStatusEntity

    suspend fun getRatedTvShows(): Pager<Int, MyRatedTvShowEntity>

    suspend fun getTvShowsByPerson(personId: Int): List<TvShowEntity>
}
