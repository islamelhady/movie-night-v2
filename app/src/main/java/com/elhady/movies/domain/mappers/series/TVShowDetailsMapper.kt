package com.elhady.movies.domain.mappers.series

import com.elhady.movies.data.remote.response.tvShow.TVShowDetailsDto
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.TVShowDetails
import com.elhady.movies.utilities.Constants
import com.elhady.movies.utilities.convertToDayMonthYearFormat
import javax.inject.Inject

class TVShowDetailsMapper @Inject constructor(): Mapper<TVShowDetailsDto, TVShowDetails> {
    override fun map(input: TVShowDetailsDto): TVShowDetails {
        return TVShowDetails(
            tvShowGenres = input.genres?.map{
                                            it?.name
            }?.joinToString(" • ") ?: "",
            tvShowId = input.id ?: 0,
            tvShowImage = (Constants.IMAGE_PATH + input.backdropPath),
            tvShowName = input.name ?: "",
            tvShowOverview = input.overview ?: "",
            tvShowReleaseDate = input.firstAirDate?.convertToDayMonthYearFormat() ?: "",
            tvShowReview = input.voteCount ?: 0,
            tvShowSeasonsNumber = input.numberOfSeasons ?: 0,
            tvShowVoteAverage = input.voteAverage.toString().take(3)
        )
    }
}