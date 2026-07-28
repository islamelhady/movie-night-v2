package com.elhady.movies.core.domain.usecase.tvshow

import com.elhady.movies.core.domain.model.tvshow.SeasonEntity
import com.elhady.movies.core.domain.repository.TvShowRepository
import javax.inject.Inject

class GetTvDetailsSeasonsUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository,
) {
    suspend operator fun invoke(tvShowId:Int): List<SeasonEntity> {
        return tvShowRepository.getTvDetailsSeasons(tvShowId)
    }
}
