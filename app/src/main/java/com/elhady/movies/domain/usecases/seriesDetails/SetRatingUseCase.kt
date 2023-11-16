package com.elhady.movies.domain.usecases.seriesDetails

import com.elhady.movies.data.repository.SeriesRepository
import com.elhady.movies.domain.mappers.movie.RatingStatusMapper
import com.elhady.movies.domain.models.RatingStatus
import javax.inject.Inject

class SetRatingUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val ratingStatusMapper: RatingStatusMapper
) {
    suspend operator fun invoke(seriesId: Int, value: Float): RatingStatus {
        val response = if (value == 0f) {
            seriesRepository.deleteRateSeries(seriesId = seriesId)
        } else {
            seriesRepository.setRatingSeries(seriesId = seriesId, value = value)
        }
        return response?.let {
            ratingStatusMapper.map(it)
        } ?: throw Throwable("not success")
    }
}