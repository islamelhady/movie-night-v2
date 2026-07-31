package com.elhady.movies.feature.search.presentation.search.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.people.People
import com.elhady.movies.core.ui.state.PeopleUiState
import javax.inject.Inject

class PeopleUiMapper @Inject constructor() :
    Mapper<People, PeopleUiState> {
    override fun map(input: People): PeopleUiState {
        return PeopleUiState(
            input.id,
            input.name,
            input.imageUrl
        )
    }
}
