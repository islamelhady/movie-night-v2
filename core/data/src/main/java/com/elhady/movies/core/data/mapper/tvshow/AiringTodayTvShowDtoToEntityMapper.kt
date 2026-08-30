package com.elhady.movies.core.data.mapper.tvshow

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.database.entity.tvshow.AiringTodayTvShowEntity
import com.elhady.movies.core.common.Mapper
import com.elhady.movies.core.network.dto.tvshow.TvShowDto
import javax.inject.Inject

class AiringTodayTvShowDtoToEntityMapper @Inject constructor():
    Mapper<TvShowDto, AiringTodayTvShowEntity> {
    override fun map(input: TvShowDto): AiringTodayTvShowEntity {
        return AiringTodayTvShowEntity(
            id = input.id ?: 0,
            title = input.name ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            rate = input.voteAverage ?: 0.0
        )
    }
}
