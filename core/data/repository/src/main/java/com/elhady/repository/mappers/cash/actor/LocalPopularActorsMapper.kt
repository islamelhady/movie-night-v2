package com.elhady.repository.mappers.cash.actor

import com.elhady.repository.Constants
import com.elhady.local.database.dto.actor.ActorLocalDto
import com.elhady.remote.ActorRemoteDto
import com.elhady.repository.mappers.Mapper
import javax.inject.Inject

class LocalPopularActorsMapper @Inject constructor(): Mapper<ActorRemoteDto, ActorLocalDto> {
    override fun map(input: ActorRemoteDto): ActorLocalDto {
        return ActorLocalDto(
            id = input.id ?: 0,
            name = input.name ?: "",
            imageUrl = Constants.IMAGE_PATH + input.profilePath,
            characterName = input.character ?: ""
        )
    }
}