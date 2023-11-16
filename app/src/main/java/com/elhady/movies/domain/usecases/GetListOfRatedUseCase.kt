package com.elhady.movies.domain.usecases

import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.mappers.movie.RatedMoviesMapper
import com.elhady.movies.domain.models.Rated
import javax.inject.Inject

class GetListOfRatedUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val ratedMoviesMapper: RatedMoviesMapper
) {

    suspend operator fun invoke(): List<Rated>{
        return movieRepository.getRatedMovie()?.map {
            ratedMoviesMapper.map(it)
        } ?: throw Throwable("not success")
    }

}