package com.elhady.movies.feature.watchlist.presentation.watchhistory.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.movie.MovieInWatchHistory
import com.elhady.movies.feature.watchlist.presentation.watchhistory.MovieUiState
import java.util.Date
import javax.inject.Inject

class MovieDomainMapper @Inject constructor() : Mapper<MovieUiState, MovieInWatchHistory> {
    override fun map(input: MovieUiState): MovieInWatchHistory {
        return MovieInWatchHistory(
            id = input.id ,
            title = input.title,
            description = input.description,
            voteAverage = input.rating,
            posterPath = input.imageUrl,
            dateWatched = Date(),
            year = input.year
        )
    }
}
