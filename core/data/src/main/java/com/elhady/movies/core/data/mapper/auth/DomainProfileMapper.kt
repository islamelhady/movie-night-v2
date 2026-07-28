package com.elhady.movies.core.data.mapper.auth

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.domain.model.auth.ProfileEntity
import com.elhady.movies.core.network.model.response.dto.profile.ProfileRemoteDto
import javax.inject.Inject

class DomainProfileMapper @Inject constructor(): Mapper<ProfileRemoteDto, ProfileEntity> {
    override fun map(input: ProfileRemoteDto): ProfileEntity {
        return ProfileEntity(
            username = "@" + input.username,
            avatarUrl = BuildConfig.IMAGE_BASE_PATH + input.avatar?.tmdb?.avatarPath
        )
    }
}
