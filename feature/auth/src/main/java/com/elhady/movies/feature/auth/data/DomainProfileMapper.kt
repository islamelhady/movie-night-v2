package com.elhady.movies.feature.auth.data

import com.elhady.movies.feature.auth.BuildConfig
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.model.response.dto.profile.ProfileRemoteDto
import com.elhady.movies.core.common.domain.entities.ProfileEntity
import javax.inject.Inject

class DomainProfileMapper @Inject constructor(): Mapper<ProfileRemoteDto, ProfileEntity> {
    override fun map(input: ProfileRemoteDto): ProfileEntity {
        return ProfileEntity(
            username = "@" + input.username,
            avatarUrl = BuildConfig.IMAGE_BASE_PATH + input.avatar?.tmdb?.avatarPath
        )
    }
}
