package com.elhady.usecase.seriesDetails

import com.elhady.entities.ActorEntity
import com.elhady.entities.MediaDetailsReviewEntity
import com.elhady.entities.MovieEntity
import com.elhady.entities.SeasonEntity
import com.elhady.entities.SeriesDetailsEntity
import com.elhady.usecase.GetReviewsUseCase
import com.elhady.usecase.MediaType
import com.elhady.usecase.repository.SeriesRepository
import javax.inject.Inject

class GetSeriesDetailsUseCase @Inject constructor(
    private val repository: SeriesRepository,
    private val seriesDetailsMapper: SeriesDetailsMapper,
    private val actorDtoMapper: ActorDtoMapper,
    private val seriesDtoMapper: SeriesDtoMapper,
    private val seasonMapper: SeasonMapper,
    private val getReviewsUseCase: GetReviewsUseCase,
) {

    suspend fun getSeriesDetails(seriesId: Int): SeriesDetailsEntity {
        val response = repository.getSeriesDetails(seriesId)
        return response?.let {
            seriesDetailsMapper.map(it)
        } ?: throw Throwable("Not success")
    }

    suspend fun getSeriesCast(seriesId: Int): List<ActorEntity> {
        return repository.getSeriesCast(seriesId)?.cast?.map(actorDtoMapper::map)
            ?: throw Throwable("not found")
    }

    suspend fun getSimilarSeries(seriesId: Int): List<MovieEntity> {
        return repository.getSimilarSeries(seriesId)?.map(seriesDtoMapper::map)
            ?: throw Throwable("not success")
    }

    suspend fun getSeasons(seriesId: Int): List<SeasonEntity> {
        return repository.getSeriesDetails(seriesId)?.seasons?.map(seasonMapper::map)
            ?: throw Throwable("not success")
    }

    suspend fun getSeriesReview(seriesId: Int): MediaDetailsReviewEntity {
        val reviews = getReviewsUseCase(mediaId = seriesId, type = MediaType.SERIES)
        return MediaDetailsReviewEntity(
            reviews = reviews.take(MAX_NUM_REVIEWS),
            isMoreThanMax = reviews.size > MAX_NUM_REVIEWS
        )
    }

    companion object{
        const val MAX_NUM_REVIEWS = 3
    }
}