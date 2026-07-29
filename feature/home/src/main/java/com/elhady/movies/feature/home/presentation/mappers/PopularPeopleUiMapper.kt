package com.elhady.movies.feature.home.presentation.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.people.People
import com.elhady.movies.feature.home.presentation.PopularPeopleUiState
import javax.inject.Inject

class PopularPeopleUiMapper @Inject constructor() :
    Mapper<People, PopularPeopleUiState> {
    override fun map(input: People): PopularPeopleUiState {
        return PopularPeopleUiState(
            input.id,
            input.imageUrl,
            input.name
        )
    }
}
