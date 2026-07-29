package com.elhady.movies.core.domain.usecase.tvshow

import com.elhady.movies.core.domain.model.tvshow.EpisodeDetails
import com.elhady.movies.core.domain.repository.TvShowRepository
import javax.inject.Inject

class GetEpisodeDetailsUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): EpisodeDetails {
        return tvShowRepository.getEpisodeDetails(seriesId, seasonNumber, episodeNumber)
    }
}
