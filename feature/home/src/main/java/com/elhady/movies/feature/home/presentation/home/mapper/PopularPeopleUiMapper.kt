package com.elhady.movies.feature.home.presentation.home.mapper

import com.elhady.movies.core.common.Mapper
import com.elhady.movies.core.domain.model.people.People
import com.elhady.movies.feature.home.presentation.home.PopularPeopleUiState
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
