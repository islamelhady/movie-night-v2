package com.elhady.usecase.repository

import androidx.paging.Pager
import com.elhady.local.database.dto.WatchHistoryLocalDto
import com.elhady.local.database.dto.series.AiringTodaySeriesLocalDto
import com.elhady.local.database.dto.series.OnTheAirSeriesLocalDto
import com.elhady.local.database.dto.series.TVSeriesListsLocalDto
import com.elhady.remote.response.CreditsDto
import com.elhady.remote.response.RatedSeriesDto
import com.elhady.remote.response.RatingDto
import com.elhady.remote.response.TrendingDto
import com.elhady.remote.response.episode.EpisodeDto
import com.elhady.remote.response.genre.GenreDto
import com.elhady.remote.response.review.ReviewDto
import com.elhady.remote.response.series.SeriesDetailsDto
import com.elhady.remote.response.series.SeriesDto
import com.elhady.remote.response.video.VideoDto

interface SeriesRepository {

    suspend fun getAiringTodaySeries(): List<AiringTodaySeriesLocalDto>

    suspend fun getOnTheAirSeries(): List<OnTheAirSeriesLocalDto>

    fun getAllOnTheAirSeries(): Pager<Int, SeriesDto>

    suspend fun getTVSeriesLists(): List<TVSeriesListsLocalDto>

    fun getAllTopRatedTV(): Pager<Int, SeriesDto>

    fun getAllPopularTV(): Pager<Int, SeriesDto>

    fun getAllLatestTV(): Pager<Int, SeriesDto>

    suspend fun getSeriesDetails(seriesId: Int): SeriesDetailsDto?

    suspend fun getSeriesCast(seriesId: Int): CreditsDto?

    suspend fun getSimilarSeries(seriesId: Int): List<SeriesDto>?

    suspend fun getSeriesReview(seriesId: Int): List<ReviewDto>?

    suspend fun getSeasonDetails(seriesId: Int, seasonNumber: Int): List<EpisodeDto>?

    suspend fun getTrendingTvSries(): List<TrendingDto>?

    fun getSeriesByGenre(genreId: Int): Pager<Int, SeriesDto>

    fun getAllSeries(): Pager<Int, SeriesDto>

    suspend fun getGenreSeries(): List<GenreDto>?

    suspend fun searchForSeriesPager(query: String): Pager<Int, SeriesDto>

    suspend fun getSeriesTrailer(seriesId: Int): VideoDto?

    suspend fun insertSeriesWatch(movie: WatchHistoryLocalDto)

    suspend fun setRatingSeries(seriesId: Int, value: Float): RatingDto?
    suspend fun deleteRateSeries(seriesId: Int): RatingDto?
    suspend fun getRatedSeries(): List<RatedSeriesDto>?

}