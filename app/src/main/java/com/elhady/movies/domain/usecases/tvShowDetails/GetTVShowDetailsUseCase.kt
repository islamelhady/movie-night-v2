package com.elhady.movies.domain.usecases.tvShowDetails

import com.elhady.movies.data.repository.SeriesRepository
import com.elhady.movies.domain.mappers.series.TVShowDetailsMapper
import com.elhady.movies.domain.models.TVShowDetails
import javax.inject.Inject

class GetTVShowDetailsUseCase @Inject constructor(private val repository: SeriesRepository, private val tvShowDetailsMapper: TVShowDetailsMapper){
    suspend operator fun invoke(tvShowId: Int): TVShowDetails{
        val response = repository.getTVShowDetails(tvShowId)
        return response?.let {
            tvShowDetailsMapper.map(it)
        } ?: throw Throwable("Not success")
    }
}