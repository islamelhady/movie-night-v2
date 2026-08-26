package com.elhady.movies.core.domain.usecase.movie

import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.core.domain.repository.MovieRepository
import com.elhady.movies.core.domain.usecase.common.RefreshIfNeededUseCase
import javax.inject.Inject

class GetNowPlayingUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val refreshIfNeededUseCase: RefreshIfNeededUseCase
) {
    suspend operator fun invoke(limit: Int = 10): List<Movie> {
        refreshIfNeededUseCase()
        return movieRepository.getNowPlayingMovies()
            .also { if (it.isEmpty()) movieRepository.refreshNowPlayingMovies() }
            .take(limit)
    }

}
