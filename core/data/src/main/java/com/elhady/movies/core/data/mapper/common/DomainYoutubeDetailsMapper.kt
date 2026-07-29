package com.elhady.movies.core.data.mapper.common

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.dto.common.YoutubeVideoDetailsDto
import com.elhady.movies.core.domain.model.common.YoutubeVideoDetailsEntity
import javax.inject.Inject

class DomainYoutubeDetailsMapper @Inject constructor() :
    Mapper<YoutubeVideoDetailsDto, YoutubeVideoDetailsEntity> {
    override fun map(input: YoutubeVideoDetailsDto): YoutubeVideoDetailsEntity {
        return YoutubeVideoDetailsEntity(
            key = input.key ?: "",
            name = input.name ?: "",
            site = input.site ?: "",
            type = input.type ?: ""
        )
    }
}
