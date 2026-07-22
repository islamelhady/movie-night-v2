package com.elhady.movies.feature.details.domain.usecase.episodedetails

import com.elhady.movies.core.common.domain.entities.RatingEpisodeDetailsStatusEntity
import com.elhady.movies.core.common.domain.repository.MovieRepository
import javax.inject.Inject

class SetEpisodeRatingUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int,
        value: Float
    ): RatingEpisodeDetailsStatusEntity {
        return movieRepository.setRatingForEpisode(seriesId, seasonNumber, episodeNumber, value)
    }
}
