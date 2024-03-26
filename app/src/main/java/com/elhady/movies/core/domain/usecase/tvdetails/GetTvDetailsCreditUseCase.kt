package com.elhady.movies.core.domain.usecase.tvdetails

import com.elhady.movies.core.domain.entities.PeopleEntity
import com.elhady.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

class GetTvDetailsCreditUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(tvShowId:Int): List<PeopleEntity> {
        return movieRepository.getTvDetailsCredit(tvShowId)
    }
}