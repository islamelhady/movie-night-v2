package com.elhady.movies.core.data.repository.mappers.domain

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.data.remote.response.dto.TvShowsCastItem
import com.elhady.movies.core.data.repository.Constants.IMAGE_BASE_PATH
import com.elhady.movies.core.domain.entities.TvShowEntity
import javax.inject.Inject

class DomainTvShowsByPeopleMapper @Inject constructor() : Mapper<TvShowsCastItem?, TvShowEntity> {
    override fun map(input: TvShowsCastItem?): TvShowEntity {

        return TvShowEntity(
            input?.id ?: 0, input?.name ?: "",
            (IMAGE_BASE_PATH + input?.posterPath),
            rate = (input?.voteAverage
                ?: 0.0) as Double
        )
    }
}
