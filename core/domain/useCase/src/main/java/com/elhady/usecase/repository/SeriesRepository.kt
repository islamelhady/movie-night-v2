package com.elhady.usecase.repository

import androidx.paging.Pager
import com.elhady.entities.MovieEntity
import com.elhady.entities.TvShowEntity
import com.elhady.local.database.dto.WatchHistoryLocalDto
import com.elhady.local.database.dto.series.AiringTodaySeriesLocalDto
import com.elhady.local.database.dto.series.OnTheAirSeriesLocalDto
import com.elhady.local.database.dto.series.TVSeriesListsLocalDto
import com.elhady.remote.response.CreditsDto
import com.elhady.remote.response.RatedSeriesDto
import com.elhady.remote.response.StatusResponse
import com.elhady.remote.response.TrendingDto
import com.elhady.remote.response.episode.EpisodeDto
import com.elhady.remote.response.genre.GenreMovieRemoteDto
import com.elhady.remote.response.review.ReviewDto
import com.elhady.remote.response.dto.tvShowDetails.SeriesDetailsDto
import com.elhady.remote.response.dto.TVShowsRemoteDto
import com.elhady.remote.response.video.VideoDto

interface SeriesRepository {

    suspend fun getAiringTodayTVShowsFromDatabase(): List<TvShowEntity>
    suspend fun refreshAiringTodayTVShows()

    suspend fun getOnTheAirTVShowsFromDatabase(): List<TvShowEntity>
    suspend fun refreshOnTheAirTVShowsTVShows()
//    fun getAllOnTheAirSeries(): Pager<Int, TVShowsRemoteDto>

    suspend fun getTVShowsListsFromDatabase(): List<TvShowEntity>
    suspend fun refreshTVShowsLists()

//    fun getAllTopRatedTV(): Pager<Int, TVShowsRemoteDto>
    //    fun getAllPopularTV(): Pager<Int, TVShowsRemoteDto>
//    fun getAllLatestTV(): Pager<Int, TVShowsRemoteDto>

//    suspend fun getSeriesDetails(seriesId: Int): SeriesDetailsDto?
//
//    suspend fun getSeriesCast(seriesId: Int): CreditsDto?
//
//    suspend fun getSimilarSeries(seriesId: Int): List<TVShowsRemoteDto>?
//
//    suspend fun getSeriesReview(seriesId: Int): List<ReviewDto>?
//
//    suspend fun getSeasonDetails(seriesId: Int, seasonNumber: Int): List<EpisodeDto>?
//
//    suspend fun getTrendingTvSries(): List<TrendingDto>?
//
//    fun getSeriesByGenre(genreId: Int): Pager<Int, TVShowsRemoteDto>
//
//    fun getAllSeries(): Pager<Int, TVShowsRemoteDto>
//
//    suspend fun getGenreSeries(): List<GenreMovieRemoteDto>?
//
//    suspend fun searchForSeriesPager(query: String): Pager<Int, TVShowsRemoteDto>
//
//    suspend fun getSeriesTrailer(seriesId: Int): VideoDto?

//    suspend fun insertSeriesWatch(movie: WatchHistoryLocalDto)
//
//    suspend fun setRatingSeries(seriesId: Int, value: Float): StatusResponse?
//    suspend fun deleteRateSeries(seriesId: Int): StatusResponse?
//    suspend fun getRatedSeries(): List<RatedSeriesDto>?

}