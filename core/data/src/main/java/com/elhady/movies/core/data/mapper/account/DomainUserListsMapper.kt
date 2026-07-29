package com.elhady.movies.core.data.mapper.account

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.dto.account.UserListDto
import com.elhady.movies.core.domain.model.account.UserList
import javax.inject.Inject

class DomainUserListsMapper @Inject constructor() : Mapper<UserListDto, UserList> {
    override fun map(input: UserListDto): UserList {
        return UserList(
            id = input.id ?: 0,
            name = input.name ?: ""
        )
    }
}
