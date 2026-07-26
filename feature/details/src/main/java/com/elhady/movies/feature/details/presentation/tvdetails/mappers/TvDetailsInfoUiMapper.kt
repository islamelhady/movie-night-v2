package com.elhady.movies.feature.details.presentation.tvdetails.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.GenreEntity
import com.elhady.movies.core.domain.model.tvdetails.TvDetailsInfoEntity
import com.elhady.movies.feature.details.presentation.tvdetails.TvDetailsUiState
import javax.inject.Inject

class TvDetailsInfoUiMapper @Inject constructor() : Mapper<TvDetailsInfoEntity, TvDetailsUiState> {
    override fun map(input: TvDetailsInfoEntity): TvDetailsUiState {
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


    private fun mapGenereToUi(genereEntities: List<GenreEntity>): List<String> {
        return genereEntities.map {
            it.genreName
        }
    }
}
