package com.elhady.movies.feature.details.presentation.moviedetails.mapper

import com.elhady.movies.core.domain.model.UserListEntity
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.common.presentation.model.UserListUi
import javax.inject.Inject

class UserListUiMapper @Inject constructor(): Mapper<UserListEntity, UserListUi> {
    override fun map(input: UserListEntity): UserListUi {
        return UserListUi(
            id = input.id,
            name = input.name
        )
    }
}
