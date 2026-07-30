package com.elhady.movies.feature.details.presentation.moviedetails.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.movie.Cast
import com.elhady.movies.core.ui.state.PeopleUiState
import javax.inject.Inject

class CastUiMapper @Inject constructor() :
    Mapper<Cast, PeopleUiState> {
    override fun map(input: Cast): PeopleUiState {
        return PeopleUiState(
            id = input.id,
            name = input.name,
            imageUrl = input.profilePath
        )
    }
}
