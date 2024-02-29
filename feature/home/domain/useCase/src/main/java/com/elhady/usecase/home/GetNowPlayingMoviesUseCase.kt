package com.elhady.usecase.home

import com.elhady.entities.MovieEntity
import com.elhady.usecase.repository.MovieRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNowPlayingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val nowPlayingMovieMapper: NowPlayingMovieMapper
) {
    suspend operator fun invoke(): List<MovieEntity> {
        return movieRepository.getNowPlayingMovies().map(nowPlayingMovieMapper::map)
    }
}