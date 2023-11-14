package com.elhady.movies.domain.usecases

import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.mappers.TrailerMapper
import com.elhady.movies.domain.models.Trailer
import javax.inject.Inject

class GetTrailerUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val trailerMapper: TrailerMapper
) {
    suspend operator fun invoke(movieId: Int): Trailer {
        val response = movieRepository.getMovieTrailer(movieId)
        return response?.let {
            trailerMapper.map(it)
        } ?: throw Throwable("error")
    }
}