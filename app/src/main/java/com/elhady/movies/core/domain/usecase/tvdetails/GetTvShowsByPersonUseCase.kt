package com.elhady.movies.core.domain.usecase.tvdetails

import com.elhady.movies.core.domain.entities.TvShowEntity
import com.elhady.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

class GetTvShowsByPersonUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(personId:Int): List<TvShowEntity> {
        return movieRepository.getTvShowsByPerson(personId)
    }
}