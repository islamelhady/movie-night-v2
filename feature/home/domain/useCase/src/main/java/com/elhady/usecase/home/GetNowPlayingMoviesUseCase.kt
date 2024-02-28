package com.elhady.usecase.home

import com.elhady.entities.MediaEntity
import com.elhady.usecase.repository.MovieRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNowPlayingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val nowPlayingMovieMapper: NowPlayingMovieMapper
) {
    suspend operator fun invoke(): List<MediaEntity> {
        return movieRepository.getNowPlayingMovies().map(nowPlayingMovieMapper::map)
    }
}