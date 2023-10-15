package com.elhady.movies.domain.mappers.actor

import com.elhady.movies.data.local.database.entity.actor.ActorEntity
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.Actor

class ActorsMapper : Mapper<ActorEntity, Actor> {
    override fun map(input: ActorEntity): Actor {
        return Actor(
            id = input.id,
            name = input.name,
            imageUrl = input.imageUrl
        )
    }
}