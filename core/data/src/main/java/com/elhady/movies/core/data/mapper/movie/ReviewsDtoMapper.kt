package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.common.Mapper
import com.elhady.movies.core.network.dto.movie.ReviewsDto
import com.elhady.movies.core.domain.model.common.Review
import com.elhady.movies.core.domain.model.movie.ReviewResponse
import javax.inject.Inject

class ReviewsDtoMapper @Inject constructor() : Mapper<ReviewsDto, ReviewResponse> {

    override fun map(input: ReviewsDto): ReviewResponse {
        return ReviewResponse(
            reviews = input.results?.map {
                Review(
                    name = it.author ?: "",
                    avatarPath = it.authorDetails?.avatarPath ?: "",
                    content = it.content ?: "",
                    createdAt = it.createdAt ?: ""
                )
            } ?: emptyList(),
            totalPages = input.totalPages ?: 0,
            page = input.page ?: 0,
            totalResults = input.totalResults ?: 0
        )
    }
}
