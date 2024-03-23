package com.elhady.movies.core.data.repository.mappers.domain

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.data.remote.response.dto.CastItem
import com.elhady.movies.core.data.repository.Constants.IMAGE_BASE_PATH
import com.elhady.movies.core.domain.entities.MovieEntity
import javax.inject.Inject

class DomainMoviesByPeopleMapper @Inject constructor() : Mapper<CastItem?, MovieEntity> {
    override fun map(input: CastItem?): MovieEntity {

        return MovieEntity(
            id = input?.id ?: 0,
            title = input?.title ?: "",
            imageUrl = (IMAGE_BASE_PATH + input?.posterPath),
            genreEntities = emptyList(),
            rate = input?.voteAverage
                ?: 0.0
        )

    }
}