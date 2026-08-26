package com.elhady.movies.core.data.mapper.common

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.dto.common.YoutubeVideoDetailsDto
import com.elhady.movies.core.domain.model.common.YoutubeVideoDetails
import javax.inject.Inject

class YoutubeDetailsDtoMapper @Inject constructor() :
    Mapper<YoutubeVideoDetailsDto, YoutubeVideoDetails> {
    override fun map(input: YoutubeVideoDetailsDto): YoutubeVideoDetails {
        return YoutubeVideoDetails(
            key = input.key ?: "",
            name = input.name ?: "",
            site = input.site ?: "",
            type = input.type ?: ""
        )
    }
}
