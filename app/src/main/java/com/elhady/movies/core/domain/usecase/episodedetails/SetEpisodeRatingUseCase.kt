package com.elhady.movies.core.domain.usecase.episodedetails

import com.elhady.movies.core.domain.entities.RatingEpisodeDetailsStatusEntity
import com.elhady.movies.core.domain.repository.MovieRepository
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