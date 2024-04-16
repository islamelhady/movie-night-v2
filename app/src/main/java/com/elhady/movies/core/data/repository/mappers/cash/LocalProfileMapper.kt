package com.elhady.movies.core.data.repository.mappers.cash

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.data.local.database.dto.ProfileLocalDto
import com.elhady.movies.core.data.remote.response.dto.profile.ProfileRemoteDto
import com.elhady.movies.core.data.repository.Constants.IMAGE_BASE_PATH
import javax.inject.Inject

class LocalProfileMapper @Inject constructor() :
    Mapper<ProfileRemoteDto, ProfileLocalDto> {
    override fun map(input: ProfileRemoteDto): ProfileLocalDto {
        return ProfileLocalDto(
            username = input.username ?: "",
            avatarUrl = IMAGE_BASE_PATH + input.avatar?.tmdb?.avatarPath
        )
    }
}