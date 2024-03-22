package com.elhady.movies.core.data.repository.mappers.domain.tv

import com.elhady.movies.core.data.local.database.dto.tvshow.TvShowsLocalDto
import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.data.remote.response.dto.TVShowsRemoteDto
import com.elhady.movies.core.data.repository.Constants.IMAGE_BASE_PATH
import com.elhady.movies.core.domain.entities.TVShowsEntity
import javax.inject.Inject

class DomainTVMapper @Inject constructor() :
    Mapper<TvShowsLocalDto, TVShowsEntity> {

    override fun map(input: TvShowsLocalDto): TVShowsEntity {
        return TVShowsEntity(
            id = input.id ?: 0,
            title = input.title ?: "",
            imageUrl = IMAGE_BASE_PATH + input.imageUrl ,
            rate = input.rate ?: 0.0,
            genreEntities = emptyList()
        )
    }
}