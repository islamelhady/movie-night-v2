package com.elhady.movies.core.domain.usecase.usecase.tvdetails

import com.elhady.movies.core.domain.entities.tvdetails.TvDetailsInfoEntity
import com.elhady.movies.core.domain.usecase.repository.MovieRepository
import javax.inject.Inject

class GetTVDetailsInfoUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(tvShowId:Int): TvDetailsInfoEntity {
        return movieRepository.getTvDetailsInfo(tvShowId)
    }
}