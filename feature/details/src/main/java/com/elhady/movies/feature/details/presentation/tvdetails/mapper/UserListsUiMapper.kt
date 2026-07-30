package com.elhady.movies.feature.details.presentation.tvdetails.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.account.UserList
import com.elhady.movies.core.ui.state.UserListUiState
import com.elhady.movies.feature.details.presentation.tvdetails.TvDetailsUiState
import javax.inject.Inject

class UserListsUiMapper @Inject constructor() : Mapper<List<UserList>, TvDetailsUiState> {
    override fun map(input: List<UserList>): TvDetailsUiState {
        return TvDetailsUiState(
            userLists = input.map(::mapUserListToUi)
        )
    }

    private fun mapUserListToUi(userListEntity: UserList): UserListUiState {
        return UserListUiState(
            id = userListEntity.id,
            name = userListEntity.name
        )
    }
}
