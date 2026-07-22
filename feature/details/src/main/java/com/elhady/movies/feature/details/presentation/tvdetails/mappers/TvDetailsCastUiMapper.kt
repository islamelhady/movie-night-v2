package com.elhady.movies.feature.details.presentation.tvdetails.mappers

import com.elhady.movies.core.common.presentation.model.PeopleUIState
import com.elhady.movies.feature.details.presentation.tvdetails.TvDetailsUiState
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.common.domain.entities.PeopleEntity
import javax.inject.Inject

class TvDetailsCastUiMapper @Inject constructor() :
    Mapper<List<PeopleEntity>, TvDetailsUiState> {

    override fun map(input: List<PeopleEntity>): TvDetailsUiState {
        return TvDetailsUiState(
            cast = mapCastToUi(input)
        )
    }

    private fun mapCastToUi(castEntity: List<PeopleEntity>): List<PeopleUIState> {
        return castEntity.map {
            PeopleUIState(
                id = it.id,
                name = it.name,
                imageUrl = it.imageUrl
            )
        }
    }


}
