package com.elhady.movies.core.data.mapper.account

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.account.CreateList
import com.elhady.movies.core.network.dto.account.ListResponse
import javax.inject.Inject

class CreateListDtoMapper @Inject constructor() : Mapper<ListResponse, CreateList> {
    override fun map(input: ListResponse): CreateList {
        return CreateList(
            listId = input.listId,
            statusCode = input.statusCode,
            statusMessage = input.statusMessage,
            success = input.success
        )
    }
}
