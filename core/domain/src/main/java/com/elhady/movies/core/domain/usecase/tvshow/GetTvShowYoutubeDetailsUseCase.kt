package com.elhady.movies.core.domain.usecase.tvshow

import com.elhady.movies.core.domain.model.common.YoutubeVideoDetailsEntity
import com.elhady.movies.core.domain.repository.TvShowRepository
import javax.inject.Inject

class GetTvShowYoutubeDetailsUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository,
) {
    suspend operator fun invoke(tvShowId: Int): YoutubeVideoDetailsEntity {
        return tvShowRepository.getTrailerVideoForTvShow(tvShowId)
    }
}
