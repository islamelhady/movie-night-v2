package com.elhady.movies.core.data.repository.mappers.cash

import com.elhady.movies.BuildConfig
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.data.local.database.dto.ProfileLocalDto
import com.elhady.movies.core.network.model.response.dto.profile.ProfileRemoteDto
import javax.inject.Inject

class LocalProfileMapper @Inject constructor() :
    Mapper<ProfileRemoteDto, ProfileLocalDto> {
    override fun map(input: ProfileRemoteDto): ProfileLocalDto {
        return ProfileLocalDto(
            username = input.username ?: "",
            avatarUrl = BuildConfig.IMAGE_BASE_PATH + input.avatar?.tmdb?.avatarPath
        )
    }
}
