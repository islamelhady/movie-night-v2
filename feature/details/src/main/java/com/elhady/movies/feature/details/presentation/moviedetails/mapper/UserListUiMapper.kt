package com.elhady.movies.feature.details.presentation.moviedetails.mapper

import com.elhady.movies.core.domain.model.account.UserList
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.ui.state.UserListUiState
import javax.inject.Inject

class UserListUiMapper @Inject constructor(): Mapper<UserList, UserListUiState> {
    override fun map(input: UserList): UserListUiState {
        return UserListUiState(
            id = input.id,
            name = input.name
        )
    }
}
