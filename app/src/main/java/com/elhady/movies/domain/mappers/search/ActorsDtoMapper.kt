package com.elhady.movies.domain.mappers.search

import com.elhady.movies.data.remote.response.actor.PersonDto
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.Media
import com.elhady.movies.utilities.Constants
import javax.inject.Inject

class ActorsDtoMapper @Inject constructor():Mapper<PersonDto, Media> {
    override fun map(input: PersonDto): Media {
        return Media(
            mediaID = input.id ?: 0,
            mediaName = input.name ?: "",
            mediaImage = (Constants.IMAGE_PATH + input.profilePath),
            mediaType = "",
            mediaRate = 0f,
            mediaDate = ""
        )
    }
}