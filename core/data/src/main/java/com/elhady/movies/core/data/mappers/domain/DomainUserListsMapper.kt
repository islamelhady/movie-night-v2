package com.elhady.movies.core.data.mappers.domain

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.model.response.dto.UserListRemoteDto
import com.elhady.movies.core.common.domain.entities.UserListEntity
import javax.inject.Inject

class DomainUserListsMapper @Inject constructor() : Mapper<UserListRemoteDto, UserListEntity> {
    override fun map(input: UserListRemoteDto): UserListEntity {
        return UserListEntity(
            id = input.id ?: 0,
            name = input.name ?: ""
        )
    }
}
