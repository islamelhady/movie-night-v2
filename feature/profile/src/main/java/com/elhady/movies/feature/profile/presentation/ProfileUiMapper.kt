package com.elhady.movies.feature.profile.presentation

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.auth.Profile
import javax.inject.Inject

class ProfileUiMapper @Inject constructor() : Mapper<Profile, ProfileUIState> {
    override fun map(input: Profile): ProfileUIState {
        return ProfileUIState(
            input.username,
            input.avatarUrl
        )
    }
}
