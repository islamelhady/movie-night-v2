package com.elhady.movies.core.domain.usecase.usecase.episodedetails

import com.elhady.movies.core.domain.entities.PeopleEntity
import com.elhady.movies.core.domain.usecase.repository.MovieRepository
import javax.inject.Inject

class GetCastForEpisodeUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(
        id: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): List<PeopleEntity> {
        return movieRepository.getCastForEpisode(id, seasonNumber, episodeNumber)
    }
}