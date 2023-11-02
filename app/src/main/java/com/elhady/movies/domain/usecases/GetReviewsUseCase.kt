package com.elhady.movies.domain.usecases

import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.data.repository.SeriesRepository
import com.elhady.movies.domain.enums.MediaType
import com.elhady.movies.domain.mappers.ReviewMapper
import com.elhady.movies.domain.models.Review
import javax.inject.Inject

class GetReviewsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val seriesRepository: SeriesRepository,
    private val reviewMapper: ReviewMapper
) {
    suspend operator fun invoke(mediaId: Int, type: MediaType): List<Review> {
        val responseMovie = movieRepository.getMovieReview(movieId = mediaId)
        val responseSeries = seriesRepository.getSeriesReview(seriesId = mediaId)

        when(type){
            MediaType.MOVIES -> {
                return responseMovie?.let { list ->
                    list.map {
                        reviewMapper.map(it)
                    }
                } ?: throw Throwable("Not success")
            }
            MediaType.SERIES -> TODO()
        }

    }
}