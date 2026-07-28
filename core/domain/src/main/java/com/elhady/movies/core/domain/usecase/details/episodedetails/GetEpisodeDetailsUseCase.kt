package com.elhady.movies.core.domain.usecase.details.episodedetails

import com.elhady.movies.core.domain.model.EpisodeDetailsEntity
import com.elhady.movies.core.domain.repository.TvShowRepository
import javax.inject.Inject

class GetEpisodeDetailsUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): EpisodeDetailsEntity {
        return tvShowRepository.getEpisodeDetails(seriesId, seasonNumber, episodeNumber)
    }
}
