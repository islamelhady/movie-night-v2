package com.elhady.movies.feature.tvshow.presentation.tvshow.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.tvshow.TvShows
import com.elhady.movies.feature.tvshow.presentation.tvshow.TvShowUi

import javax.inject.Inject

class TvShowUiMapper @Inject constructor() :
    Mapper<TvShows, TvShowUi> {
    override fun map(input: TvShows): TvShowUi {
        return TvShowUi(
            tvId = input.id,
            imageUrl = input.imageUrl,
            rate = input.rate
        )
    }
}
