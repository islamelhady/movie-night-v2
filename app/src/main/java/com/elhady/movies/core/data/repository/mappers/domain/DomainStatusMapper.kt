package com.elhady.movies.core.data.repository.mappers.domain

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.data.remote.response.dto.StatusResponse
import com.elhady.movies.core.domain.entities.StatusEntity
import javax.inject.Inject

class DomainStatusMapper @Inject constructor() : Mapper<StatusResponse, StatusEntity> {
    override fun map(input: StatusResponse): StatusEntity {
        return StatusEntity(
            statusCode = input.statusCode?:0,
            statusMessage = input.statusMessage?:"",
            success = input.success?:false
        )
    }
}