package com.elhady.usecase.seriesDetails

import com.elhady.usecase.repository.SeriesRepository
import javax.inject.Inject

class GetSeriesRateUseCase @Inject constructor(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(seriesId: Int): Float {
        val response = seriesRepository.getRatedSeries()
        return response?.let { list ->
            list.find { it.id == seriesId }?.rating ?: 0F
        } ?: throw Throwable("Error")
    }

}