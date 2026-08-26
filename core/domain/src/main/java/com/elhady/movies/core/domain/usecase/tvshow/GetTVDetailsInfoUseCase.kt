package com.elhady.movies.core.domain.usecase.tvshow

import com.elhady.movies.core.domain.model.tvshow.TvDetailsInfo
import com.elhady.movies.core.domain.repository.TvShowRepository
import javax.inject.Inject

class GetTvDetailsInfoUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository,
) {
    suspend operator fun invoke(tvShowId:Int): TvDetailsInfo {
        return tvShowRepository.getTvDetailsInfo(tvShowId)
    }
}
