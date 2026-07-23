package com.elhady.movies.core.data.mappers.domain

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.model.response.moviedetails.ReviewsRemoteDto
import com.elhady.movies.core.domain.model.ReviewEntity
import com.elhady.movies.core.domain.model.moviedetails.ReviewResponseEntity
import javax.inject.Inject

class DomainReviewsMapper @Inject constructor() : Mapper<ReviewsRemoteDto, ReviewResponseEntity> {
    override fun map(input: ReviewsRemoteDto): ReviewResponseEntity {
        return ReviewResponseEntity(
            reviews = input.results?.map {
                ReviewEntity(
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
