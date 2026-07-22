package com.elhady.movies.core.data.repository.mappers.domain

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.model.response.dto.YoutubeVideoDetailsRemoteDto
import com.elhady.movies.core.common.domain.entities.YoutubeVideoDetailsEntity
import javax.inject.Inject

class DomainYoutubeDetailsMapper @Inject constructor() :
    Mapper<YoutubeVideoDetailsRemoteDto, YoutubeVideoDetailsEntity> {
    override fun map(input: YoutubeVideoDetailsRemoteDto): YoutubeVideoDetailsEntity {
        return YoutubeVideoDetailsEntity(
            key = input.key ?: "",
            name = input.name ?: "",
            site = input.site ?: "",
            type = input.type ?: ""
        )
    }
}
