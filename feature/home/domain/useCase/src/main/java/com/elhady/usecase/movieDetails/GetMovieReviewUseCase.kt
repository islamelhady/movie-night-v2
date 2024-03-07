package com.elhady.usecase.movieDetails

import com.elhady.entities.MediaDetailsReviewEntity
import com.elhady.usecase.GetReviewsUseCase
import com.elhady.usecase.MediaType
import com.elhady.usecase.seriesDetails.GetSeriesDetailsUseCase
import javax.inject.Inject

class GetMovieReviewUseCase @Inject constructor(
    private val getMovieReviewsUseCase: GetReviewsUseCase
) {
    suspend fun getMovieReview(movieId: Int): MediaDetailsReviewEntity {
        val reviews = getMovieReviewsUseCase(mediaId = movieId, type = MediaType.MOVIES)
        return MediaDetailsReviewEntity(reviews = reviews.take(GetSeriesDetailsUseCase.MAX_NUM_REVIEWS), isMoreThanMax = reviews.size > GetSeriesDetailsUseCase.MAX_NUM_REVIEWS)
    }
}