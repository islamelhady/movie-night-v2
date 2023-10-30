package com.elhady.movies.domain.usecases.movieDetails

import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.mappers.actor.ActorDtoMapper
import com.elhady.movies.domain.mappers.movie.MovieDetailsDtoMapper
import com.elhady.movies.domain.mappers.movie.MovieDtoMapper
import com.elhady.movies.domain.models.Actor
import com.elhady.movies.domain.models.Media
import com.elhady.movies.domain.models.MovieDetails
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val movieDetailsDtoMapper: MovieDetailsDtoMapper,
    private val actorDtoMapper: ActorDtoMapper,
    private val movieDtoMapper: MovieDtoMapper
) {
    suspend operator fun invoke(movieId: Int): MovieDetails {
        val response = movieRepository.getDetailsMovies(movieId)
        return response?.let {
            movieDetailsDtoMapper.map(response)
        } ?: throw Throwable("Not Success")
    }

    suspend fun getMovieCast(movieId: Int): List<Actor> {

        val response = movieRepository.getMovieCast(movieId)?.cast
        return response?.let { list ->
            list.map {
                actorDtoMapper.map(it)
            }
        } ?: throw Throwable("Not success")
    }

    suspend fun getSimilarMovies(movieId: Int): List<Media> {
        val response = movieRepository.getSimilarMovies(movieId)
        return response?.let { list ->
            list.map {
                movieDtoMapper.map(it)
            }
        } ?: throw Throwable("Not success")
    }


}