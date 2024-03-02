package com.elhady.movies.domain.mappers.movie

import com.elhady.movies.data.remote.response.RatedMovieDto
import com.elhady.movies.domain.enums.MediaType
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.Rated
import com.elhady.movies.utilities.Constants
import javax.inject.Inject

class RatedMoviesMapper @Inject constructor() : Mapper<RatedMovieDto, Rated> {
    override fun map(input: RatedMovieDto): Rated {
        return Rated(
            id = input.id ?: 0,
            title = input.title ?: "",
            posterPath = Constants.IMAGE_PATH + input.backdropPath,
            rating = input.rating ?: 0F,
            releaseDate = input.releaseDate ?: "",
            mediaType = MediaType.MOVIES.value
        )
    }
}