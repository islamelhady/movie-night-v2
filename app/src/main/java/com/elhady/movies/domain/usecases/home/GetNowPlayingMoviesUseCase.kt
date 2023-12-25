package com.elhady.movies.domain.usecases.home

import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.mappers.NowPlayingMovieMapper
import com.elhady.movies.domain.models.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNowPlayingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val nowPlayingMovieMapper: NowPlayingMovieMapper
) {
    suspend operator fun invoke(): List<Media> {
        return movieRepository.getNowPlayingMovies().map(nowPlayingMovieMapper::map)
    }
}