package com.elhady.movies.core.domain.usecase.details.tvdetails

import com.elhady.movies.core.domain.model.tvdetails.TvDetailsInfoEntity
import com.elhady.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

class GetTVDetailsInfoUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(tvShowId:Int): TvDetailsInfoEntity {
        return movieRepository.getTvDetailsInfo(tvShowId)
    }
}
