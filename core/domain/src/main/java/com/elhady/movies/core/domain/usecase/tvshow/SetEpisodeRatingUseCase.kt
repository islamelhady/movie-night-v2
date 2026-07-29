package com.elhady.movies.core.domain.usecase.tvshow

import com.elhady.movies.core.domain.model.tvshow.RatingEpisodeDetailsStatus
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
    ): RatingEpisodeDetailsStatus {
        return tvShowRepository.setRatingForEpisode(seriesId, seasonNumber, episodeNumber, value)
    }
}
