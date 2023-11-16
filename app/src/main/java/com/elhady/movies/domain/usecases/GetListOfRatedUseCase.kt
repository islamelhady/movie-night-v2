package com.elhady.movies.domain.usecases

import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.models.Rated
import javax.inject.Inject

class GetListOfRatedUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {


    suspend operator fun invoke(): List<Rated>{
        return movieRepository.getRatedMovie()
    }

}