package com.elhady.movies.data.local.mappers.actors

import com.elhady.movies.data.local.database.entity.actor.ActorEntity
import com.elhady.movies.data.remote.response.PersonDto
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.utilities.Constants
import javax.inject.Inject

class ActorsMapper @Inject constructor(): Mapper<PersonDto, ActorEntity> {
    override fun map(input: PersonDto): ActorEntity {
        return ActorEntity(
            id = input.id ?: 0,
            name = input.name ?: "",
            imageUrl = (Constants.IMAGE_PATH + input.profilePath)
        )
    }
}