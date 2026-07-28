package com.elhady.movies.core.domain.usecase.details.episodedetails

import com.elhady.movies.core.domain.model.PeopleEntity
import com.elhady.movies.core.domain.repository.TvShowRepository
import javax.inject.Inject

class GetCastForEpisodeUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(
        id: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): List<PeopleEntity> {
        return tvShowRepository.getCastForEpisode(id, seasonNumber, episodeNumber)
    }
}
