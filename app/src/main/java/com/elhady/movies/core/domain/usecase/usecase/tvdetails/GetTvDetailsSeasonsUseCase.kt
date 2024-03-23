package com.elhady.movies.core.domain.usecase.usecase.tvdetails

import com.elhady.movies.core.domain.entities.SeasonEntity
import com.elhady.movies.core.domain.usecase.repository.MovieRepository
import javax.inject.Inject

class GetTvDetailsSeasonsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(tvShowId:Int): List<SeasonEntity> {
        return movieRepository.getTvDetailsSeasons(tvShowId)
    }
}