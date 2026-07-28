package com.elhady.movies.core.domain.usecase.tvshow

import com.elhady.movies.core.domain.model.tvshow.RatingEpisodeDetailsStatusEntity
import com.elhady.movies.core.domain.repository.TvShowRepository
import javax.inject.Inject

class SetEpisodeRatingUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int,
        value: Float
    ): RatingEpisodeDetailsStatusEntity {
        return tvShowRepository.setRatingForEpisode(seriesId, seasonNumber, episodeNumber, value)
    }
}
