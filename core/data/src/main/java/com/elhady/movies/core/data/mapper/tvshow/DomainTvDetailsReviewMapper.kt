package com.elhady.movies.core.data.mapper.tvshow

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.dto.common.TvReviewDto
import com.elhady.movies.core.domain.model.common.Review
import javax.inject.Inject

class DomainTvDetailsReviewMapper @Inject constructor() : Mapper<TvReviewDto, Review> {
    override fun map(input: List<TvReviewDto>): List<Review> {
        return input.map(::map)
    }

    override fun map(input: TvReviewDto): Review {
        return Review(
            name = input.authorDetails?.username ?: "User",
            avatarPath = input.authorDetails?.avatarPath ?: "",
            content = input.content ?: "",
            createdAt = input.createdAt ?: ""
        )
    }

}
