package com.elhady.movies.domain.usecases.movieDetails

import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.mappers.movie.RatingStatusMoviesMapper
import com.elhady.movies.domain.models.RatingStatus
import javax.inject.Inject

class SetRatingUseCase @Inject constructor(private val movieRepository: MovieRepository, private val ratingStatusMoviesMapper: RatingStatusMoviesMapper) {
    suspend operator fun invoke(): RatingStatus{
        movieRepository.setRateMovie(movieId = , value = )
    }
}