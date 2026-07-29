package com.elhady.movies.core.domain.usecase.tvshow

import com.elhady.movies.core.domain.model.common.YoutubeVideoDetailsEntity
import com.elhady.movies.core.domain.repository.TvShowRepository
import javax.inject.Inject

class GetEpisodeVideoUseCase  @Inject constructor(
    private val tvShowRepository: TvShowRepository
)  {
    suspend operator fun invoke(
        id: Int,
        seasonNumber: Int,
        episodeNumber: Int,
    ): YoutubeVideoDetailsEntity {
        return tvShowRepository.getVideoEpisodeDetails(id, seasonNumber, episodeNumber)
    }
}
