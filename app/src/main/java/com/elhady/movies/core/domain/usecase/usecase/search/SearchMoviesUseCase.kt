package com.elhady.movies.core.domain.usecase.usecase.search

import com.elhady.movies.core.domain.entities.MovieEntity
import com.elhady.movies.core.domain.usecase.repository.MovieRepository
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(
        keyword: String,
        genreId: Int? = null
    ): List<MovieEntity> {

        return movieRepository.searchForMovies(keyword)
            // Filter the search results.
            .filter { movie ->
                // If a genreId is provided, check if the movie's genre list contains it.
                movie.genreEntities.takeIf { genreId != null }
                    ?.map { it.genreID }
                    ?.contains(genreId)
                        // If no genreId is provided, or the movie's genre list contains the genreId,
                        // and the movie's rate is not 0.0, include the movie in the filter results.
                        ?: true && movie.rate != 0.0
            }
            // Sort the filtered results by the movie rate in descending order.
            .sortedByDescending { it.rate }
    }
}