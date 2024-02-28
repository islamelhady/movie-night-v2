package com.elhady.usecase.movieDetails

import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.mappers.movie.RatingStatusMapper
import com.elhady.movies.domain.models.RatingStatus
import javax.inject.Inject

class SetRatingUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val ratingStatusMapper: RatingStatusMapper
) {
    suspend operator fun invoke(movieId: Int, value: Float): RatingStatus {
        val response = if (value == 0f) {
            movieRepository.deleteRateMovie(movieId = movieId)
        } else {
            movieRepository.setRateMovie(movieId = movieId, value = value)
        }
        return response?.let {
            ratingStatusMapper.map(it)
        } ?: throw Throwable("not success")
    }
}