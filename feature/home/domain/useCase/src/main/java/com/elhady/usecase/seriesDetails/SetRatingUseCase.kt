package com.elhady.usecase.seriesDetails

import com.elhady.entities.RatingStatusEntity
import com.elhady.usecase.repository.SeriesRepository
import javax.inject.Inject

class SetRatingUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val ratingStatusMapper: RatingStatusMapper
) {
    suspend operator fun invoke(seriesId: Int, value: Float): RatingStatusEntity {
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