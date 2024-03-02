package com.elhady.movies.domain.mappers

import com.elhady.movies.data.remote.response.TrendingDto
import com.elhady.movies.domain.enums.MediaType
import com.elhady.movies.domain.models.Media
import com.elhady.movies.utilities.Constants
import javax.inject.Inject

class TrendingMediaMapper @Inject constructor() : Mapper<TrendingDto, Media> {
    override fun map(input: TrendingDto): Media {
        return Media(
            mediaID = input.id ?: 0,
            mediaName = input.originalLanguage ?: input.originalTitle ?: "",
            mediaImage = (Constants.IMAGE_PATH + input.posterPath),
            mediaType = input.mediaType ?: MediaType.MOVIES.value,
            mediaRate = input.voteAverage?.toFloat() ?: 0f,
            mediaDate = input.releaseDate?.substringBefore('-') ?: ""
        )
    }

}