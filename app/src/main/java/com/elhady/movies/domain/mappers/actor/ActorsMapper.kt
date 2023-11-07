package com.elhady.movies.domain.mappers.actor

import com.elhady.movies.data.local.database.entity.actor.ActorEntity
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.Actor
import javax.inject.Inject

class ActorsMapper @Inject constructor(): Mapper<ActorEntity, Actor> {
    override fun map(input: ActorEntity): Actor {
        return Actor(
            id = input.id,
            name = input.name,
            imageUrl = input.imageUrl,
            characterName = input.characterName
        )
    }
}