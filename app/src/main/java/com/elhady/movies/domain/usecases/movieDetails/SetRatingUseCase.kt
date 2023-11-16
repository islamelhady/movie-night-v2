package com.elhady.movies.domain.usecases.movieDetails

import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.mappers.movie.RatingStatusMoviesMapper
import com.elhady.movies.domain.models.RatingStatus
import javax.inject.Inject

class SetRatingUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val ratingStatusMoviesMapper: RatingStatusMoviesMapper
) {
    suspend operator fun invoke(movieId: Int, value: Float): RatingStatus {
        val response = if (value == 0f) {
            movieRepository.deleteRateMovie(movieId = movieId)
        } else {
            movieRepository.setRateMovie(movieId = movieId, value = value)
        }
        return response?.let {
            ratingStatusMoviesMapper.map(it)
        } ?: throw Throwable("not success")
    }
}