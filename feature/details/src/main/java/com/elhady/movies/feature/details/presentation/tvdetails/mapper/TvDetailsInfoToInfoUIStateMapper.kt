package com.elhady.movies.feature.details.presentation.tvdetails.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.tvshow.TvDetailsInfo
import com.elhady.movies.feature.details.presentation.tvdetails.state.InfoUIState
import javax.inject.Inject

class TvDetailsInfoToInfoUIStateMapper @Inject constructor() : Mapper<TvDetailsInfo, InfoUIState.Info> {
    override fun map(input: TvDetailsInfo): InfoUIState.Info {
        return InfoUIState.Info(
            backdropImageUrl = input.backdropImageUrl,
            name = input.name,
            rating = input.rating,
            description = input.description,
            genres = input.genres.map { it.genreName },
        )
    }
}
