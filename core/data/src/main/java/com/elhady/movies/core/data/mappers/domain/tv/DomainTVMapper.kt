package com.elhady.movies.core.data.mappers.domain.tv

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.database.dto.tvshow.TvShowsLocalDto
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.TVShowsEntity
import javax.inject.Inject

class DomainTVMapper @Inject constructor() :
    Mapper<TvShowsLocalDto, TVShowsEntity> {

    override fun map(input: TvShowsLocalDto): TVShowsEntity {
        return TVShowsEntity(
            id = input.id ?: 0,
            title = input.title ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.imageUrl ,
            rate = input.rate ?: 0.0,
            genreEntities = emptyList()
        )
    }
}
