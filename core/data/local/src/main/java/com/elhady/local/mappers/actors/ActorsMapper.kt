package com.elhady.local.mappers.actors

import com.elhady.local.Constants
import com.elhady.local.database.dto.actor.ActorLocalDto
import com.elhady.local.mappers.Mapper
import com.elhady.remote.ActorRemoteDto
import javax.inject.Inject

class ActorsMapper @Inject constructor(): Mapper<ActorRemoteDto, ActorLocalDto> {
    override fun map(input: ActorRemoteDto): ActorLocalDto {
        return ActorLocalDto(
            id = input.id ?: 0,
            name = input.name ?: "",
            imageUrl = (Constants.IMAGE_PATH + input.profilePath),
            characterName = input.character ?: ""
        )
    }
}