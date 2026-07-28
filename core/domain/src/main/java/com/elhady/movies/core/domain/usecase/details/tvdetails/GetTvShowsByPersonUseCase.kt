package com.elhady.movies.core.domain.usecase.details.tvdetails

import com.elhady.movies.core.domain.model.TvShowEntity
import com.elhady.movies.core.domain.repository.TvShowRepository
import javax.inject.Inject

class GetTvShowsByPersonUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository,
) {
    suspend operator fun invoke(personId:Int): List<TvShowEntity> {
        return tvShowRepository.getTvShowsByPerson(personId)
    }
}
