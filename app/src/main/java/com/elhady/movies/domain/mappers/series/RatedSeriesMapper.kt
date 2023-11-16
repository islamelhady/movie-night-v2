package com.elhady.movies.domain.mappers.series

import com.elhady.movies.data.remote.response.RatedSeriesDto
import com.elhady.movies.domain.enums.MediaType
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.Rated
import com.elhady.movies.utilities.Constants
import javax.inject.Inject

class RatedSeriesMapper @Inject constructor() : Mapper<RatedSeriesDto, Rated> {
    override fun map(input: RatedSeriesDto): Rated {
        return Rated(
            id = input.id ?: 0,
            title = input.name ?: "",
            posterPath = Constants.IMAGE_PATH + input.backdropPath,
            rating = input.rating ?: 0F,
            releaseDate = input.firstAirDate ?: "",
            mediaType = MediaType.SERIES.value
        )
    }
}