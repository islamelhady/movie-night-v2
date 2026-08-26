package com.elhady.movies.core.domain.usecase.tvshow

import com.elhady.movies.core.domain.model.tvshow.SeasonDetails
import com.elhady.movies.core.domain.repository.TvShowRepository
import javax.inject.Inject

class GetSeasonDetailsUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(seriesId : Int, seasonNumber : Int): SeasonDetails {
        return tvShowRepository.getSeasonDetails(seriesId,seasonNumber)
    }
}
