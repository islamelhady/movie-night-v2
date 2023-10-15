package com.elhady.movies.domain.mappers.series

import com.elhady.movies.data.local.database.entity.series.TVSeriesListsEntity
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.Media
import javax.inject.Inject

class TVSeriesListsMapper @Inject constructor() : Mapper<TVSeriesListsEntity, Media> {
    override fun map(input: TVSeriesListsEntity): Media {
        return Media(
            mediaID = input.id,
            mediaName = input.name,
            mediaImage = input.imageUrl,
            mediaDate = "",
            mediaRate = 0f,
            mediaType = ""
        )
    }
}