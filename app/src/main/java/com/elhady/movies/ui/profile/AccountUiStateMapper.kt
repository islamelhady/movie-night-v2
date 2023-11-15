package com.elhady.movies.ui.profile

import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.Account
import javax.inject.Inject

class AccountUiStateMapper @Inject constructor() : Mapper<Account, ProfileUiState> {
    override fun map(input: Account): ProfileUiState {
        return ProfileUiState(
            avatarPath = input.avatarPath,
            name = input.name,
            username = input.username
        )
    }
}