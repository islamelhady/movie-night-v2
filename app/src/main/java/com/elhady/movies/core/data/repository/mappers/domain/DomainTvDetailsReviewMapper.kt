package com.elhady.movies.core.data.repository.mappers.domain

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.data.remote.response.dto.TvReviewRemoteDto
import com.elhady.movies.core.domain.entities.ReviewEntity
import javax.inject.Inject

class DomainTvDetailsReviewMapper @Inject constructor() : Mapper<TvReviewRemoteDto, ReviewEntity> {
    override fun map(input: List<TvReviewRemoteDto>): List<ReviewEntity> {
        return input.map(::map)
    }

    override fun map(input: TvReviewRemoteDto): ReviewEntity {
        return ReviewEntity(
            name = input.authorDetails?.username ?: "User",
            avatarPath = input.authorDetails?.avatarPath ?: "",
            content = input.content ?: "",
            createdAt = input.createdAt ?: ""
        )
    }

}