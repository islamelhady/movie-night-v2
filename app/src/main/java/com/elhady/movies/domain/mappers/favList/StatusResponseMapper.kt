package com.elhady.movies.domain.mappers.favList

import com.elhady.movies.data.remote.response.StatusResponseDto
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.StatusResponse
import javax.inject.Inject

class StatusResponseMapper @Inject constructor() : Mapper<StatusResponseDto, StatusResponse> {
    override fun map(input: StatusResponseDto): StatusResponse {
        return StatusResponse(
            statusCode = input.statusCode ?: 0,
            statusMessage = input.statusMessage ?: "",
            success = input.success ?: false
        )
    }
}