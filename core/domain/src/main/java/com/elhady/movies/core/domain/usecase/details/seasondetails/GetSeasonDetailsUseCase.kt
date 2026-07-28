package com.elhady.movies.core.domain.usecase.details.seasondetails

import com.elhady.movies.core.domain.model.seasondetails.SeasonDetailsEntity
import com.elhady.movies.core.domain.repository.TvShowRepository
import javax.inject.Inject

class GetSeasonDetailsUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(seriesId : Int, seasonNumber : Int): SeasonDetailsEntity {
        return tvShowRepository.getSeasonDetails(seriesId,seasonNumber)
    }
}
