package com.elhady.movies.domain.mappers

import com.elhady.movies.data.local.database.entity.TrendingMovieEntity
import com.elhady.movies.domain.models.Media
import javax.inject.Inject

class TrendingMovieMapper @Inject constructor() : Mapper<TrendingMovieEntity, Media> {
    override fun map(input: TrendingMovieEntity): Media {
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