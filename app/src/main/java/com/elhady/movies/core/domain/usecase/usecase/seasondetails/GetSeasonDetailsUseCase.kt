package com.elhady.movies.core.domain.usecase.usecase.seasondetails

import com.elhady.movies.core.domain.entities.seasondetails.SeasonDetailsEntity
import com.elhady.movies.core.domain.usecase.repository.MovieRepository
import javax.inject.Inject

class GetSeasonDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(seriesId : Int, seasonNumber : Int): SeasonDetailsEntity {
        return movieRepository.getSeasonDetails(seriesId,seasonNumber)
    }
}