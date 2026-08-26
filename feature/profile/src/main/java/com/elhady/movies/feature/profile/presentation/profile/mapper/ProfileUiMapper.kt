package com.elhady.movies.feature.profile.presentation.profile.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.auth.Profile
import com.elhady.movies.feature.profile.presentation.profile.ProfileUiState
import javax.inject.Inject

class ProfileUiMapper @Inject constructor() : Mapper<Profile, ProfileUiState> {
    override fun map(input: Profile): ProfileUiState {
        return ProfileUiState(
            input.username,
            input.avatarUrl
        )
    }
}
