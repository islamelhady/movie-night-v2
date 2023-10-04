package com.elhady.movies.domain.mappers

import com.elhady.movies.data.local.database.entity.UpcomingMovieEntity
import com.elhady.movies.domain.models.Media
import javax.inject.Inject

class UpcomingMovieMapper @Inject constructor(): Mapper<UpcomingMovieEntity, Media> {
    override fun map(input: UpcomingMovieEntity): Media {
        return Media(
            mediaID = input.id,
            mediaName = input.title,
            mediaImage = input.imageUrl,
            mediaRate = 0f,
            mediaDate = "",
            mediaType = ""
        )
    }
}