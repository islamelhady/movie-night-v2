package com.elhady.movies.domain.usecases.movieDetails

import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.models.MovieDetails

class InsertMoviesUseCase constructor(private val movieRepository: MovieRepository) {
    operator fun invoke(movie: MovieDetails){
        movieRepository.insertMovieWatch()
    }
}