package com.elhady.movies.core.domain.usecase.movie

import com.elhady.movies.core.domain.model.GenreEntity
import com.elhady.movies.core.domain.repository.GenreRepository
import javax.inject.Inject

class GetAllGenresMoviesUseCase @Inject constructor(
    private val genreRepository: GenreRepository
) {
    suspend operator fun invoke(): List<GenreEntity> {
        genreRepository.refreshGenres()
        return genreRepository.getGenresMovies().sortedBy { it.genreName }
    }
}
