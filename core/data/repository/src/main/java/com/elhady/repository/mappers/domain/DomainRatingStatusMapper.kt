package com.elhady.repository.mappers.domain

import com.elhady.movies.domain.mappers.Mapper
import com.elhady.remote.response.StatusResponse
import javax.inject.Inject

class DomainRatingStatusMapper @Inject constructor() : Mapper<StatusResponse, RatingStatus> {
    override fun map(input: StatusResponse): RatingStatus {
        return RatingStatus(
            statusCode = input.statusCode ?: 0,
            statusMessage = input.statusMessage ?: "",
        )
    }
}