package com.elhady.movies.core.data.mapper.tvshow

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.database.entity.tvshow.AiringTodayTvShowEntity
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.dto.tvshow.TVShowsDto
import javax.inject.Inject

class LocalAiringTodayTvShowMapper @Inject constructor():
    Mapper<TVShowsDto, AiringTodayTvShowEntity> {
    override fun map(input: TVShowsDto): AiringTodayTvShowEntity {
        return AiringTodayTvShowEntity(
            id = input.id ?: 0,
            title = input.name ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            rate = input.voteAverage ?: 0.0
        )
    }
}
