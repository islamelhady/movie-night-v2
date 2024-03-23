package com.elhady.movies.presentation.viewmodel.tvdetails.mappers

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.UserListEntity
import com.elhady.movies.presentation.viewmodel.common.model.UserListUi
import com.elhady.movies.presentation.viewmodel.tvdetails.TvDetailsUiState
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