package com.elhady.movies.core.data.repository.mappers.domain

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.data.remote.response.dto.UserListRemoteDto
import com.elhady.movies.core.domain.entities.UserListEntity
import javax.inject.Inject

class DomainUserListsMapper @Inject constructor() : Mapper<UserListRemoteDto, UserListEntity> {
    override fun map(input: UserListRemoteDto): UserListEntity {
        return UserListEntity(
            id = input.id ?: 0,
            name = input.name ?: ""
        )
    }
}