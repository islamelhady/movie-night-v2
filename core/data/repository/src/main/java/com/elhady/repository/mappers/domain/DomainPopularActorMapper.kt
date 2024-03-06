package com.elhady.repository.mappers.domain

import com.elhady.entities.ActorEntity
import com.elhady.repository.Constants
import com.elhady.local.database.dto.actor.ActorLocalDto
import com.elhady.repository.mappers.Mapper
import javax.inject.Inject

class DomainPopularActorMapper @Inject constructor(): Mapper<ActorLocalDto, ActorEntity> {
    override fun map(input: ActorLocalDto): ActorEntity {
        return ActorEntity(
            id = input.id ?: 0,
            name = input.name ?: "unknown",
            imageUrl = Constants.IMAGE_PATH + input.imageUrl,
            characterName = input.characterName ?: ""
        )
    }
}