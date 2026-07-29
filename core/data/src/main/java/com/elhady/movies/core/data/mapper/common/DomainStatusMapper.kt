package com.elhady.movies.core.data.mapper.common

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.dto.common.StatusResponse
import com.elhady.movies.core.domain.model.common.Status
import javax.inject.Inject

class DomainStatusMapper @Inject constructor() : Mapper<StatusResponse, Status> {
    override fun map(input: StatusResponse): Status {
        return Status(
            statusCode = input.statusCode?:0,
            statusMessage = input.statusMessage?:"",
            success = input.success?:false
        )
    }
}
