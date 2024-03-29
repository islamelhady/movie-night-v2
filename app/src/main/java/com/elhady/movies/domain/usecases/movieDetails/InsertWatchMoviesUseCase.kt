package com.elhady.movies.domain.usecases.movieDetails

import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.mappers.movie.WatchMovieHistoryMapper
import com.elhady.movies.domain.models.MovieDetails
import javax.inject.Inject

class InsertWatchMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val watchHistoryMapper: WatchMovieHistoryMapper
) {
    suspend operator fun invoke(movie: MovieDetails) {
        val movieWatch = watchHistoryMapper.map(movie)
        movieRepository.insertMovieWatch(movieWatch)
    }
}