package com.elhady.usecase.movieDetails

import com.elhady.entities.ActorEntity
import com.elhady.entities.MediaDetailsReviewEntity
import com.elhady.entities.MediaEntity
import com.elhady.entities.MovieDetailsEntity
import com.elhady.usecase.GetReviewsUseCase
import com.elhady.usecase.MediaType
import com.elhady.usecase.repository.MovieRepository
import com.elhady.usecase.seriesDetails.GetSeriesDetailsUseCase.Companion.MAX_NUM_REVIEWS
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val movieDetailsDtoMapper: MovieDetailsDtoMapper,
    private val actorDtoMapper: ActorDtoMapper,
    private val movieDtoMapper: MovieDtoMapper,
    private val getMovieReviewsUseCase: GetReviewsUseCase
) {
    suspend fun getMovieDetails(movieId: Int): MovieDetailsEntity {
        val response = movieRepository.getDetailsMovies(movieId)
        return response?.let {
            movieDetailsDtoMapper.map(response)
        } ?: throw Throwable("Not Success")
    }

    suspend fun getMovieCast(movieId: Int): List<ActorEntity> {

        val response = movieRepository.getMovieCast(movieId)?.cast
        return response?.let (actorDtoMapper::map)
         ?: throw Throwable("Not success")
    }

    suspend fun getSimilarMovies(movieId: Int): List<MediaEntity> {
        val response = movieRepository.getSimilarMovies(movieId)
        return response?.let ( movieDtoMapper::map) ?: throw Throwable("Not success")
    }

    suspend fun getMovieReview(movieId: Int): MediaDetailsReviewEntity{
        val reviews = getMovieReviewsUseCase(mediaId = movieId, type = MediaType.MOVIES)
        return MediaDetailsReviewEntity(reviews = reviews.take(MAX_NUM_REVIEWS), isMoreThanMax = reviews.size > MAX_NUM_REVIEWS)
    }

}