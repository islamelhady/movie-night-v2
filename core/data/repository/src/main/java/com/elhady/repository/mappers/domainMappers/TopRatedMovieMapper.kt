package com.elhady.movies.domain.mappers

import com.elhady.movies.data.local.database.entity.movies.TopRatedMovieEntity
import com.elhady.movies.domain.models.Media
import javax.inject.Inject

class TopRatedMovieMapper @Inject constructor() : Mapper<TopRatedMovieEntity, Media> {
    override fun map(input: TopRatedMovieEntity): Media {
        return Media(
            mediaID = input.id,
            mediaName = input.name,
            mediaImage = input.imageUrl,
            mediaType = "",
            mediaDate = "",
            mediaRate = 0f
        )
    }
}