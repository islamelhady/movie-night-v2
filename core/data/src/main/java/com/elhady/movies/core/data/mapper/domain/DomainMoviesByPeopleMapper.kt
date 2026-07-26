package com.elhady.movies.core.data.mapper.domain

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.model.response.dto.CastItem
import com.elhady.movies.core.domain.model.MovieEntity
import javax.inject.Inject

class DomainMoviesByPeopleMapper @Inject constructor() : Mapper<CastItem?, MovieEntity> {
    override fun map(input: CastItem?): MovieEntity {

        return MovieEntity(
            id = input?.id ?: 0,
            title = input?.title ?: "",
            imageUrl = (BuildConfig.IMAGE_BASE_PATH + input?.posterPath),
            genreEntities = emptyList(),
            rate = input?.voteAverage
                ?: 0.0
        )

    }
}
