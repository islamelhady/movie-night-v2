package com.elhady.movies.domain.usecases.seriesDetails

import com.elhady.movies.data.repository.SeriesRepository
import com.elhady.movies.domain.enums.MediaType
import com.elhady.movies.domain.mappers.ReviewMapper
import com.elhady.movies.domain.mappers.actor.ActorDtoMapper
import com.elhady.movies.domain.mappers.movie.MovieDtoMapper
import com.elhady.movies.domain.mappers.series.SeasonMapper
import com.elhady.movies.domain.mappers.series.SeriesDetailsMapper
import com.elhady.movies.domain.mappers.series.SeriesDtoMapper
import com.elhady.movies.domain.models.Actor
import com.elhady.movies.domain.models.Media
import com.elhady.movies.domain.models.MediaDetailsReview
import com.elhady.movies.domain.models.Review
import com.elhady.movies.domain.models.Season
import com.elhady.movies.domain.models.SeriesDetails
import com.elhady.movies.domain.usecases.GetReviewsUseCase
import com.elhady.movies.utilities.Constants
import com.elhady.movies.utilities.Constants.MAX_NUM_REVIEWS
import javax.inject.Inject

class GetSeriesDetailsUseCase @Inject constructor(
    private val repository: SeriesRepository,
    private val seriesDetailsMapper: SeriesDetailsMapper,
    private val actorDtoMapper: ActorDtoMapper,
    private val seriesDtoMapper: SeriesDtoMapper,
    private val seasonMapper: SeasonMapper,
    private val getReviewsUseCase: GetReviewsUseCase,
) {

    suspend fun getSeriesDetails(seriesId: Int): SeriesDetails {
        val response = repository.getSeriesDetails(seriesId)
        return response?.let {
            seriesDetailsMapper.map(it)
        } ?: throw Throwable("Not success")
    }

    suspend fun getSeriesCast(seriesId: Int): List<Actor> {
        return repository.getSeriesCast(seriesId)?.cast?.map {
            actorDtoMapper.map(it)
        } ?: throw Throwable("not found")
    }

    suspend fun getSimilarSeries(seriesId: Int): List<Media> {
        return repository.getSimilarSeries(seriesId)?.map {
            seriesDtoMapper.map(it)
        } ?: throw Throwable("not success")
    }

    suspend fun getSeasons(seriesId: Int): List<Season>{
        return repository.getSeriesDetails(seriesId)?.seasons?.map {
            seasonMapper.map(it)
        } ?: throw Throwable("not success")
    }

    suspend fun getSeriesReview(seriesId: Int): MediaDetailsReview{
        val reviews = getReviewsUseCase(mediaId = seriesId, type = MediaType.SERIES)
        return MediaDetailsReview(reviews = reviews.take(MAX_NUM_REVIEWS), isMoreThanMax =  reviews.size > MAX_NUM_REVIEWS)
    }

}