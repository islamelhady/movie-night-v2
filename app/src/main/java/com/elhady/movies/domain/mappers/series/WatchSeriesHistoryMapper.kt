package com.elhady.movies.domain.mappers.series

import com.elhady.movies.data.local.database.entity.WatchHistoryEntity
import com.elhady.movies.domain.enums.MediaType
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.SeriesDetails
import com.elhady.movies.utilities.Constants
import javax.inject.Inject

class WatchSeriesHistoryMapper @Inject constructor() : Mapper<SeriesDetails, WatchHistoryEntity> {
    override fun map(input: SeriesDetails): WatchHistoryEntity {
        return WatchHistoryEntity(
            id = input.seriesId,
            image = Constants.IMAGE_PATH + input.seriesImage,
            title = input.seriesImage,
            voteAverage = input.seriesVoteAverage,
            releaseDate = input.seriesReleaseDate,
            movieDuration = input.seriesSeasonsNumber,
            mediaType = MediaType.SERIES.value
        )
    }
}