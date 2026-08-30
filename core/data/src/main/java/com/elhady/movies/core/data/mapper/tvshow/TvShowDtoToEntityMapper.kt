package com.elhady.movies.core.data.mapper.tvshow

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.common.Mapper
import com.elhady.movies.core.database.entity.tvshow.TvShowEntity
import com.elhady.movies.core.network.dto.tvshow.TvShowDto
import javax.inject.Inject

class TvShowDtoToEntityMapper @Inject constructor():
    Mapper<TvShowDto, TvShowEntity> {
    override fun map(input: TvShowDto): TvShowEntity {
        return TvShowEntity(
            id = input.id ?: 0,
            title = input.name ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            rate = input.voteAverage ?: 0.0
        )
    }
}
