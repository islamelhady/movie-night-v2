package com.elhady.movies.core.data.mapper.tvshow

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.dto.common.TvReviewDto
import com.elhady.movies.core.domain.model.common.ReviewEntity
import javax.inject.Inject

class DomainTvDetailsReviewMapper @Inject constructor() : Mapper<TvReviewDto, ReviewEntity> {
    override fun map(input: List<TvReviewDto>): List<ReviewEntity> {
        return input.map(::map)
    }

    override fun map(input: TvReviewDto): ReviewEntity {
        return ReviewEntity(
            name = input.authorDetails?.username ?: "User",
            avatarPath = input.authorDetails?.avatarPath ?: "",
            content = input.content ?: "",
            createdAt = input.createdAt ?: ""
        )
    }

}
