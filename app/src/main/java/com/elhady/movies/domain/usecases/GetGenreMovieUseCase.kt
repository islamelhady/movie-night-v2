package com.elhady.movies.domain.usecases

import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.models.Genre
import javax.inject.Inject

class GetGenreMovieUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(): List<Genre> {
        return repository.getGenreMovies()?.map {
            Genre(id = it.id ?: 0, name = it.name ?: "")

        } ?: throw Throwable("not success")
    }
}