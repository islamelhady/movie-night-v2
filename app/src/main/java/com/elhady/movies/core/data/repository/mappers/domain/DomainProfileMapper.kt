package com.elhady.movies.core.data.repository.mappers.domain

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.data.remote.response.dto.profile.ProfileRemoteDto
import com.elhady.movies.core.data.repository.Constants.IMAGE_BASE_PATH
import com.elhady.movies.core.domain.entities.ProfileEntity
import javax.inject.Inject

class DomainProfileMapper @Inject constructor(): Mapper<ProfileRemoteDto, ProfileEntity> {
    override fun map(input: ProfileRemoteDto): ProfileEntity {
        return ProfileEntity(
            username = "@" + input.username,
            avatarUrl = IMAGE_BASE_PATH + input.avatar?.tmdb?.avatarPath
        )
    }
}