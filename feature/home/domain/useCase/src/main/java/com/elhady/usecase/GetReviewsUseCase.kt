package com.elhady.usecase

import com.elhady.entities.ReviewEntity
import com.elhady.usecase.repository.MovieRepository
import com.elhady.usecase.repository.SeriesRepository
import javax.inject.Inject

class GetReviewsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val seriesRepository: SeriesRepository,
    private val reviewMapper: ReviewMapper
) {
    suspend operator fun invoke(mediaId: Int, type: MediaType): List<ReviewEntity> {
        val review = when (type) {
            MediaType.MOVIES -> movieRepository.getMovieReview(movieId = mediaId)
            MediaType.SERIES -> seriesRepository.getSeriesReview(seriesId = mediaId)
        }

        return review?.let {
                reviewMapper.map(it)
        } ?: throw Throwable("not success")
    }

}

