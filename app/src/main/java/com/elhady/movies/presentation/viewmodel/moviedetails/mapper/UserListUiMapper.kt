package com.elhady.movies.presentation.viewmodel.moviedetails.mapper

import com.elhady.movies.core.domain.entities.UserListEntity
import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.presentation.viewmodel.common.model.UserListUi
import javax.inject.Inject

class UserListUiMapper @Inject constructor(): Mapper<UserListEntity, UserListUi> {
    override fun map(input: UserListEntity): UserListUi {
        return UserListUi(
            id = input.id,
            name = input.name
        )
    }
}