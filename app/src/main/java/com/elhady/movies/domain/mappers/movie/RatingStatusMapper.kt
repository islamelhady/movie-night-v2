package com.elhady.movies.domain.mappers.movie

import com.elhady.movies.data.remote.response.RatingDto
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.RatingStatus
import javax.inject.Inject

class RatingStatusMapper @Inject constructor() : Mapper<RatingDto, RatingStatus> {
    override fun map(input: RatingDto): RatingStatus {
        return RatingStatus(
            statusCode = input.statusCode ?: 0,
            statusMessage = input.statusMessage ?: "",
        )
    }
}