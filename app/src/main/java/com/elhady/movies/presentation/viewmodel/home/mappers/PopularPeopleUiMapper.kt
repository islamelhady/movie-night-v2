package com.elhady.movies.presentation.viewmodel.home.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.common.domain.entities.PeopleEntity
import com.elhady.movies.presentation.viewmodel.home.PopularPeopleUiState
import javax.inject.Inject

class PopularPeopleUiMapper @Inject constructor() :
    Mapper<PeopleEntity, PopularPeopleUiState> {
    override fun map(input: PeopleEntity): PopularPeopleUiState {
        return PopularPeopleUiState(
            input.id,
            input.imageUrl,
            input.name
        )
    }
}
