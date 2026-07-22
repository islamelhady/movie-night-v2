package com.elhady.movies.feature.details.domain.usecase.episodedetails

import android.util.Log
import com.elhady.movies.core.common.domain.entities.EpisodeDetailsEntity
import com.elhady.movies.core.common.domain.repository.MovieRepository
import javax.inject.Inject

class GetEpisodeDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): EpisodeDetailsEntity {
        Log.d(
            "banan-data-usecase",
            movieRepository.getEpisodeDetails(seriesId, seasonNumber, episodeNumber).episodeName
        )
        return movieRepository.getEpisodeDetails(seriesId, seasonNumber, episodeNumber)
    }
}
