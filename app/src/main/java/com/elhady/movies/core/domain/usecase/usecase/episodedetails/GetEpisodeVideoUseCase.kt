package com.elhady.movies.core.domain.usecase.usecase.episodedetails

import com.elhady.movies.core.domain.entities.YoutubeVideoDetailsEntity
import com.elhady.movies.core.domain.usecase.repository.MovieRepository
import javax.inject.Inject

class GetEpisodeVideoUseCase  @Inject constructor(
    private val movieRepository: MovieRepository
)  {
    suspend operator fun invoke(
        id: Int,
        seasonNumber: Int,
        episodeNumber: Int,
    ): YoutubeVideoDetailsEntity {
        return movieRepository.getVideoEpisodeDetails(id, seasonNumber, episodeNumber)
    }
}