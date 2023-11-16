package com.elhady.movies.domain.usecases.seriesDetails

import com.elhady.movies.data.repository.SeriesRepository
import javax.inject.Inject

class GetSeriesRateUseCase @Inject constructor(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(seriesId: Int): Float {
        val response = seriesRepository.getRatedSeries()
        return response?.let { list ->
            list.find { it.id == seriesId }?.rating ?: 0F
        } ?: throw Throwable("Error")
    }

}