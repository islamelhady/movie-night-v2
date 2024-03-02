package com.elhady.movies.domain.mappers

import com.elhady.movies.data.remote.response.review.ReviewDto
import com.elhady.movies.domain.models.Review
import javax.inject.Inject

class ReviewMapper @Inject constructor() : Mapper<ReviewDto, Review> {
    override fun map(input: ReviewDto): Review {
        return Review(
            userName = input.authorDetails?.username ?: "GUEST",
            userImage = input.authorDetails?.avatarPath ?: "",
            name = input.authorDetails?.name ?: "GUESt",
            content = input.content ?: "",
            rating = input.authorDetails?.rating?.toFloat() ?: 0f,
            createDate = input.createdAt?.take(10) ?: ""
        )
    }
}