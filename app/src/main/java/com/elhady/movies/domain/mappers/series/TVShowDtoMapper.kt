package com.elhady.movies.domain.mappers.series

import com.elhady.movies.data.remote.response.series.SeriesDto
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.Media
import com.elhady.movies.utilities.Constants
import javax.inject.Inject

class TVShowDtoMapper @Inject constructor():Mapper<SeriesDto, Media> {
    override fun map(input: SeriesDto): Media {
        return Media(
            mediaID = input.id ?: 0,
            mediaName = input.name ?: "",
            mediaImage = (Constants.IMAGE_PATH + input.posterPath),
            mediaType = "",
            mediaRate = 0f,
            mediaDate = ""
        )
    }
}