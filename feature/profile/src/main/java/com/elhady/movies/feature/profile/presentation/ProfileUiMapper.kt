package com.elhady.movies.feature.profile.presentation

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.common.domain.entities.ProfileEntity
import javax.inject.Inject

class ProfileUiMapper @Inject constructor() : Mapper<ProfileEntity, ProfileUIState> {
    override fun map(input: ProfileEntity): ProfileUIState {
        return ProfileUIState(
            input.username,
            input.avatarUrl
        )
    }
}
