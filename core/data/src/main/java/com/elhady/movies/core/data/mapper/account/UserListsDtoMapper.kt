package com.elhady.movies.core.data.mapper.account

import com.elhady.movies.core.common.Mapper
import com.elhady.movies.core.network.dto.account.UserListDto
import com.elhady.movies.core.domain.model.account.UserList
import javax.inject.Inject

class UserListsDtoMapper @Inject constructor() : Mapper<UserListDto, UserList> {
    override fun map(input: UserListDto): UserList {
        return UserList(
            id = input.id ?: 0,
            name = input.name ?: ""
        )
    }
}
