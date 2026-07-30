package com.elhady.movies.feature.details.presentation.tvdetails.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.common.Genre
import com.elhady.movies.core.domain.model.tvshow.TvDetailsInfo
import com.elhady.movies.feature.details.presentation.tvdetails.TvDetailsUiState
import javax.inject.Inject

class TvDetailsInfoUiMapper @Inject constructor() : Mapper<TvDetailsInfo, TvDetailsUiState> {
    override fun map(input: TvDetailsInfo): TvDetailsUiState {
        return TvDetailsUiState(
            info = TvDetailsUiState.Info(
                backdropImageUrl = input.backdropImageUrl,
                name = input.name,
                rating = input.rating,
                description = input.description,
                genres = mapGenreToUi(input.genres)
            ),
        )
    }


    private fun mapGenreToUi(genreEntities: List<Genre>): List<String> {
        return genreEntities.map {
            it.genreName
        }
    }
}
