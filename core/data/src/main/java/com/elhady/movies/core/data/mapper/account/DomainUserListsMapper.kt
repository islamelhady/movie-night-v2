package com.elhady.movies.core.data.mapper.account

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.dto.account.UserListDto
import com.elhady.movies.core.domain.model.account.UserListEntity
import javax.inject.Inject

class DomainUserListsMapper @Inject constructor() : Mapper<UserListDto, UserListEntity> {
    override fun map(input: UserListDto): UserListEntity {
        return UserListEntity(
            id = input.id ?: 0,
            name = input.name ?: ""
        )
    }
}
