package com.elhady.movies.presentation.viewmodel.profile

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.ProfileEntity
import javax.inject.Inject

class ProfileUiMapper @Inject constructor() : Mapper<ProfileEntity, ProfileUIState> {
    override fun map(input: ProfileEntity): ProfileUIState {
        return ProfileUIState(
            input.username,
            input.avatarUrl
        )
    }
}