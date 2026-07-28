package com.elhady.movies.core.domain.usecase.details.tvdetails

import com.elhady.movies.core.domain.model.SeasonEntity
import com.elhady.movies.core.domain.repository.TvShowRepository
import javax.inject.Inject

class GetTvDetailsSeasonsUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository,
) {
    suspend operator fun invoke(tvShowId:Int): List<SeasonEntity> {
        return tvShowRepository.getTvDetailsSeasons(tvShowId)
    }
}
