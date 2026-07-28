package com.elhady.movies.core.domain.usecase.tvshow

import com.elhady.movies.core.domain.model.people.PeopleEntity
import com.elhady.movies.core.domain.repository.TvShowRepository
import javax.inject.Inject

class GetTvDetailsCreditUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository,
) {
    suspend operator fun invoke(tvShowId:Int): List<PeopleEntity> {
        return tvShowRepository.getTvDetailsCredit(tvShowId)
    }
}
