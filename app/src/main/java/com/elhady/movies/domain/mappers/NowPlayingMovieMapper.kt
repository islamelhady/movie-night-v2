package com.elhady.movies.domain.mappers

import com.elhady.movies.data.local.database.entity.NowPlayingMovieEntity
import com.elhady.movies.domain.models.Media
import javax.inject.Inject

class NowPlayingMovieMapper @Inject constructor() : Mapper<NowPlayingMovieEntity, Media> {
    override fun map(input: NowPlayingMovieEntity): Media {
        return Media(
            mediaID = input.id,
            mediaName = input.name,
            mediaImage = input.imageUrl,
            mediaRate = 0f,
            mediaDate = "",
            mediaType = ""
        )
    }
}