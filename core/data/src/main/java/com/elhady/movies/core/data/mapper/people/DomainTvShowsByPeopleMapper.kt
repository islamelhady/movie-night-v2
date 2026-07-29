package com.elhady.movies.core.data.mapper.people

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.dto.people.TvShowsCastItem
import com.elhady.movies.core.domain.model.tvshow.TvShowEntity
import javax.inject.Inject

class DomainTvShowsByPeopleMapper @Inject constructor() : Mapper<TvShowsCastItem, TvShowEntity> {

    override fun map(input: List<TvShowsCastItem>): List<TvShowEntity> {
        return input.map(::map)
    }

    override fun map(input: TvShowsCastItem): TvShowEntity {
        return TvShowEntity(
            id = input.id ?: 0,
            name = input.name ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            rate = (input.voteAverage as? Double)?.times(0.5) ?: 0.0
        )
    }
}
