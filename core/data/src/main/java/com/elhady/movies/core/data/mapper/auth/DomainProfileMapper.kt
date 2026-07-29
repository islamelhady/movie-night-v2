package com.elhady.movies.core.data.mapper.auth

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.domain.model.auth.Profile
import com.elhady.movies.core.network.dto.account.ProfileDto
import javax.inject.Inject

class DomainProfileMapper @Inject constructor(): Mapper<ProfileDto, Profile> {
    override fun map(input: ProfileDto): Profile {
        return Profile(
            username = "@" + input.username,
            avatarUrl = BuildConfig.IMAGE_BASE_PATH + input.avatar?.tmdb?.avatarPath
        )
    }
}
