package com.elhady.movies.feature.details.domain.usecase.tvdetails

import com.elhady.movies.core.common.domain.entities.TvShowEntity
import com.elhady.movies.core.common.domain.repository.MovieRepository
import javax.inject.Inject

class GetTvShowsByPersonUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(personId:Int): List<TvShowEntity> {
        return movieRepository.getTvShowsByPerson(personId)
    }
}
