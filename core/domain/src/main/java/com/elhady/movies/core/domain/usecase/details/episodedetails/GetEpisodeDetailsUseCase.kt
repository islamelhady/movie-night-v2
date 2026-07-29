package com.elhady.movies.core.domain.usecase.details.episodedetails

import com.elhady.movies.core.domain.model.EpisodeDetailsEntity
import com.elhady.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

class GetEpisodeDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): EpisodeDetailsEntity {
        return movieRepository.getEpisodeDetails(seriesId, seasonNumber, episodeNumber)
    }
}
