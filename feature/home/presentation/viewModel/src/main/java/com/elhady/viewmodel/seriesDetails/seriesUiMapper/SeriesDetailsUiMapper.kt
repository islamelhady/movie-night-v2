package com.elhady.viewmodel.seriesDetails.seriesUiMapper

import com.elhady.mapper.Mapper
import com.elhady.viewmodel.seriesDetails.SeriesDetailsResultUiState
import javax.inject.Inject

class SeriesDetailsUiMapper @Inject constructor():
    Mapper<SeriesDetails, SeriesDetailsResultUiState> {
    override fun map(input: SeriesDetails): SeriesDetailsResultUiState {
        return SeriesDetailsResultUiState(
            seriesId = input.seriesId,
            seriesVoteAverage = input.seriesVoteAverage,
            seriesReview = input.seriesReview,
            seriesSeasonsNumber = input.seriesSeasonsNumber,
            seriesReleaseDate = input.seriesReleaseDate,
            seriesOverview = input.seriesOverview,
            seriesName = input.seriesName,
            seriesImage = input.seriesImage,
            seriesGenres = input.seriesGenres
        )
    }
}