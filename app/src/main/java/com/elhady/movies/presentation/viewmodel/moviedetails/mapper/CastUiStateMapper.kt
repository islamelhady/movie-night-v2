package com.elhady.movies.presentation.viewmodel.moviedetails.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.common.domain.entities.moviedetails.CastEntity
import com.elhady.movies.core.common.presentation.model.PeopleUIState
import javax.inject.Inject

class CastUiStateMapper@Inject constructor() :
    Mapper<CastEntity, PeopleUIState> {
    override fun map(input: CastEntity): PeopleUIState {
        return PeopleUIState(
            id = input.id,
            name = input.name,
            imageUrl = input.profilePath
        )
    }

}
