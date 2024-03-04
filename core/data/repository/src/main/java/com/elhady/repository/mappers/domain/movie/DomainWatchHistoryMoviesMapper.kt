package com.elhady.repository.mappers.domain.movie

import com.elhady.local.Constants
import com.elhady.local.database.dto.WatchHistoryLocalDto
import com.elhady.movies.domain.enums.MediaType
import com.elhady.movies.domain.mappers.Mapper
import javax.inject.Inject

class DomainWatchHistoryMoviesMapper @Inject constructor() : Mapper<WatchHistoryLocalDto, Wat> {
    override fun map(input: WatchHistoryLocalDto): WatchHistoryEntity {
        return WatchHistoryEntity(
            id = input.id,
            image = Constants.IMAGE_PATH + input.image,
            title = input.name,
            voteAverage = input.voteAverage,
            releaseDate = input.releaseDate,
            movieDuration = input.duration,
            mediaType = MediaType.MOVIES.value
        )
    }
}