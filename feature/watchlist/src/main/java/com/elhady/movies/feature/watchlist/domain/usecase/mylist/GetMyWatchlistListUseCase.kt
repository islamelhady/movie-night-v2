package com.elhady.movies.feature.watchlist.domain.usecase.mylist

import com.elhady.movies.core.common.domain.entities.MovieEntity
import com.elhady.movies.core.common.domain.repository.MovieRepository
import javax.inject.Inject

class GetMyWatchlistListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(): List<MovieEntity> {
        return  movieRepository.getWatchlistMovies() + movieRepository.getWatchlistTv()
    }
}
