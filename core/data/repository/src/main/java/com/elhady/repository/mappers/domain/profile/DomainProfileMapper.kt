package com.elhady.repository.mappers.domain.profile

import com.elhady.entities.ProfileEntity
import com.elhady.remote.response.profile.ProfileRemoteDto
import com.elhady.repository.Constants
import com.elhady.repository.mappers.Mapper
import javax.inject.Inject

class DomainProfileMapper @Inject constructor() : Mapper<ProfileRemoteDto, ProfileEntity> {
    override fun map(input: ProfileRemoteDto): ProfileEntity {
        return ProfileEntity(
            avatarPath = Constants.IMAGE_PATH + input.avatar?.avatarPath?.avatarPath,
            name = input.name ?: "",
            username = "@" + input.username
        )
    }
}