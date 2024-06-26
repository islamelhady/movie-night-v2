package com.elhady.movies.core.domain.usecase.tvdetails

import com.elhady.movies.core.domain.entities.TvShowEntity
import com.elhady.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

class GetTvShowRecommendationsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(tvShowId:Int):List<TvShowEntity>{
        return movieRepository.getTvShowRecommendations(tvShowId)
    }
}