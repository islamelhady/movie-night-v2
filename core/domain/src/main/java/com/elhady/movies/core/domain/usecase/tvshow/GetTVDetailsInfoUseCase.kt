package com.elhady.movies.core.domain.usecase.tvshow

import com.elhady.movies.core.domain.model.tvshow.TvDetailsInfoEntity
import com.elhady.movies.core.domain.repository.TvShowRepository
import javax.inject.Inject

class GetTVDetailsInfoUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository,
) {
    suspend operator fun invoke(tvShowId:Int): TvDetailsInfoEntity {
        return tvShowRepository.getTvDetailsInfo(tvShowId)
    }
}
