package com.elhady.movies.core.domain.usecase.search

import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.core.domain.repository.SearchRepository
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(
        keyword: String,
        genreId: Int? = null
    ): List<Movie> {
        return searchRepository.searchForMovies(keyword)
            .filter { movie ->
                ((genreId == null) || movie.genreEntities.any { it.genreID == genreId }) && movie.rate != 0.0
            }
            .sortedByDescending { it.rate }
    }
}
