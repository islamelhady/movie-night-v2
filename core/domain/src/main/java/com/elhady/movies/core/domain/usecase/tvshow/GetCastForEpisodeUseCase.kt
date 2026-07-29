package com.elhady.movies.core.domain.usecase.tvshow

import com.elhady.movies.core.domain.model.people.PeopleEntity
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
