package com.elhady.movies.data.repository

import androidx.paging.Pager
import com.elhady.movies.data.local.database.entity.WatchHistoryEntity
import com.elhady.movies.data.local.database.entity.series.AiringTodaySeriesEntity
import com.elhady.movies.data.local.database.entity.series.OnTheAirSeriesEntity
import com.elhady.movies.data.local.database.entity.series.TVSeriesListsEntity
import com.elhady.movies.data.remote.response.CreditsDto
import com.elhady.movies.data.remote.response.RatedSeriesDto
import com.elhady.movies.data.remote.response.RatingDto
import com.elhady.movies.data.remote.response.TrendingDto
import com.elhady.movies.data.remote.response.episode.EpisodeDto
import com.elhady.movies.data.remote.response.genre.GenreDto
import com.elhady.movies.data.remote.response.movie.MovieDto
import com.elhady.movies.data.remote.response.review.ReviewDto
import com.elhady.movies.data.remote.response.series.SeriesDetailsDto
import com.elhady.movies.data.remote.response.series.SeriesDto
import com.elhady.movies.data.remote.response.video.VideoDto
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface SeriesRepository {

    suspend fun getAiringTodaySeries(): List<AiringTodaySeriesEntity>

    suspend fun getOnTheAirSeries(): List<OnTheAirSeriesEntity>

    fun getAllOnTheAirSeries(): Pager<Int, SeriesDto>

    suspend fun getTVSeriesLists(): List<TVSeriesListsEntity>

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

    suspend fun insertSeriesWatch(movie: WatchHistoryEntity)

    suspend fun setRatingSeries(seriesId: Int, value: Float): RatingDto?
    suspend fun deleteRateSeries(seriesId: Int): RatingDto?
    suspend fun getRatedSeries(): List<RatedSeriesDto>?

}