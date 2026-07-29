package com.elhady.movies.core.data.mapper.tvshow

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.database.entity.tvshow.AiringTodayTvShowEntity
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.tvshow.TvShows
import javax.inject.Inject

class DomainAiringTodayTVMapper @Inject constructor() :
    Mapper<AiringTodayTvShowEntity, TvShows> {

    override fun map(input: AiringTodayTvShowEntity): TvShows {
        return TvShows(
            id = input.id ?: 0,
            title = input.title ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.imageUrl ,
            rate = input.rate ?: 0.0,
            genreEntities = emptyList()
        )
    }
}
