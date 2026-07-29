package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.dto.movie.ReviewsDto
import com.elhady.movies.core.domain.model.common.ReviewEntity
import com.elhady.movies.core.domain.model.movie.ReviewResponseEntity
import javax.inject.Inject

class DomainReviewsMapper @Inject constructor() : Mapper<ReviewsDto, ReviewResponseEntity> {
    override fun map(input: ReviewsDto): ReviewResponseEntity {
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
