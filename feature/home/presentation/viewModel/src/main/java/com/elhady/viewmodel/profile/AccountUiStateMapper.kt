package com.elhady.viewmodel.profile

import com.elhady.mapper.Mapper
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