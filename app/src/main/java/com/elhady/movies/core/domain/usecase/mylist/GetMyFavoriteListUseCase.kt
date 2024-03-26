package com.elhady.movies.core.domain.usecase.mylist

import com.elhady.movies.core.domain.entities.MovieEntity
import com.elhady.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

class GetMyFavoriteListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(): List<MovieEntity> {
        return  movieRepository.getFavoriteMovies() + movieRepository.getFavoriteTv()
    }
}