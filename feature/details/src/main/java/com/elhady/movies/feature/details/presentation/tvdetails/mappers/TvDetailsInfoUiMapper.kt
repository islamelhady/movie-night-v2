package com.elhady.movies.feature.details.presentation.tvdetails.mappers

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
                genres = mapGenereToUi(input.genres)
            ),
        )
    }


    private fun mapGenereToUi(genereEntities: List<Genre>): List<String> {
        return genereEntities.map {
            it.genreName
        }
    }
}
