package com.elhady.repository.mappers.domain

import com.elhady.entities.RatedEntity
import com.elhady.movies.domain.enums.MediaType
import com.elhady.movies.domain.mappers.Mapper
import javax.inject.Inject

class RatedMoviesMapper @Inject constructor() : Mapper<Ratl, RatedEntity> {
    override fun map(input: RatedMovieDto): Rated {
        return RatedEntity(
            id = input.id ?: 0,
            title = input.title ?: "",
            posterPath = Constants.IMAGE_PATH + input.backdropPath,
            rating = input.rating ?: 0F,
            releaseDate = input.releaseDate ?: "",
            mediaType = MediaType.MOVIES.value
        )
    }
}