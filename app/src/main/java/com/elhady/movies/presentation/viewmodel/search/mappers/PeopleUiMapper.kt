package com.elhady.movies.presentation.viewmodel.search.mappers

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.PeopleEntity
import com.elhady.movies.presentation.viewmodel.common.model.PeopleUIState
import javax.inject.Inject

class PeopleUiMapper @Inject constructor() :
    Mapper<PeopleEntity, PeopleUIState> {
    override fun map(input: PeopleEntity): PeopleUIState {
        return PeopleUIState(
            input.id,
            input.name,
            input.imageUrl
        )
    }
}