package com.elhady.movies.domain.mappers.movie

import com.elhady.movies.data.local.database.entity.WatchHistoryEntity
import com.elhady.movies.domain.enums.MediaType
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.MovieDetails
import com.elhady.movies.utilities.Constants
import javax.inject.Inject

class WatchMovieHistoryMapper @Inject constructor() : Mapper<MovieDetails, WatchHistoryEntity> {
    override fun map(input: MovieDetails): WatchHistoryEntity {
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