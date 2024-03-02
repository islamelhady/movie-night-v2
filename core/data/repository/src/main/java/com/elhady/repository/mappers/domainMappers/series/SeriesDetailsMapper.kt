package com.elhady.movies.domain.mappers.series

import com.elhady.movies.data.remote.response.series.SeriesDetailsDto
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.SeriesDetails
import com.elhady.movies.utilities.Constants
import com.elhady.movies.utilities.convertToDayMonthYearFormat
import javax.inject.Inject

class SeriesDetailsMapper @Inject constructor(private val seasonMapper: SeasonMapper) : Mapper<SeriesDetailsDto, SeriesDetails> {
    override fun map(input: SeriesDetailsDto): SeriesDetails {
        return SeriesDetails(
            seriesGenres = input.genres?.map{
                                            it?.name }?.joinToString(" • ") ?: "",
            seriesId = input.id ?: 0,
            seriesImage = (Constants.IMAGE_PATH + input.backdropPath),
            seriesName = input.name ?: "",
            seriesOverview = input.overview ?: "",
            seriesReleaseDate = input.firstAirDate?.convertToDayMonthYearFormat() ?: "",
            seriesReview = input.voteCount ?: 0,
            seriesSeasonsNumber = input.numberOfSeasons ?: 0,
            seriesVoteAverage = input.voteAverage.toString().take(3),
            seriesSeasons = input.seasons?.map {
                seasonMapper.map(it)
            } ?: emptyList()
        )
    }
}