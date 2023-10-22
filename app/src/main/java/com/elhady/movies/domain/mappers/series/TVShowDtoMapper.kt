package com.elhady.movies.domain.mappers.series

import com.elhady.movies.data.remote.response.tvShow.TVShowDto
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.Media
import com.elhady.movies.utilities.Constants
import javax.inject.Inject

class TVShowDtoMapper @Inject constructor():Mapper<TVShowDto, Media> {
    override fun map(input: TVShowDto): Media {
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