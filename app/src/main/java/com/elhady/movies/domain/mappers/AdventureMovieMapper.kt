package com.elhady.movies.domain.mappers

import com.elhady.movies.data.local.database.entity.AdventureMovieEntity
import com.elhady.movies.domain.models.Media
import javax.inject.Inject

class AdventureMovieMapper @Inject constructor() : Mapper<AdventureMovieEntity, Media> {
    override fun map(input: AdventureMovieEntity): Media {
        return Media(
            mediaID = input.id,
            mediaName = input.name,
            mediaImage = input.imageUrl,
            mediaDate = "",
            mediaRate = 0f,
            mediaType = ""
        )
    }
}