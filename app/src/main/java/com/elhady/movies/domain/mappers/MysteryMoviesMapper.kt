package com.elhady.movies.domain.mappers

import com.elhady.movies.data.local.database.entity.MysteryMovieEntity
import com.elhady.movies.domain.models.Media
import javax.inject.Inject

class MysteryMoviesMapper @Inject constructor() : Mapper<MysteryMovieEntity, Media> {
    override fun map(input: MysteryMovieEntity): Media {
        return Media(
            mediaID = input.id,
            mediaName = input.name,
            mediaImage = input.imageUrl,
            mediaType = "",
            mediaRate = 0f,
            mediaDate = ""
        )
    }
}