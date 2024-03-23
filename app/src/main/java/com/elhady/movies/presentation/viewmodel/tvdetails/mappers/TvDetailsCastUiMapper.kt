package com.elhady.movies.presentation.viewmodel.tvdetails.mappers

import com.elhady.movies.presentation.viewmodel.common.model.PeopleUIState
import com.elhady.movies.presentation.viewmodel.tvdetails.TvDetailsUiState
import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.PeopleEntity
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