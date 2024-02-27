package com.elhady.local.mappers.actors

import com.elhady.local.Constants
import com.elhady.local.database.entity.actor.ActorEntity
import com.elhady.local.mappers.Mapper
import com.elhady.remote.PersonDto
import javax.inject.Inject

class ActorsMapper @Inject constructor(): Mapper<PersonDto, ActorEntity> {
    override fun map(input: PersonDto): ActorEntity {
        return ActorEntity(
            id = input.id ?: 0,
            name = input.name ?: "",
            imageUrl = (Constants.IMAGE_PATH + input.profilePath),
            characterName = input.character ?: ""
        )
    }
}