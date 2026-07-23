package com.elhady.movies.feature.details.presentation.tvdetails.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.UserListEntity
import com.elhady.movies.core.common.presentation.model.UserListUi
import com.elhady.movies.feature.details.presentation.tvdetails.TvDetailsUiState
import javax.inject.Inject

class UserListsUiMapper @Inject constructor() : Mapper<List<UserListEntity>, TvDetailsUiState> {
    override fun map(input: List<UserListEntity>): TvDetailsUiState {
        return TvDetailsUiState(
            userLists = input.map(::mapUserListToUi)
        )
    }

    private fun mapUserListToUi(userListEntity: UserListEntity): UserListUi {
        return UserListUi(
            id = userListEntity.id,
            name = userListEntity.name
        )
    }
}
