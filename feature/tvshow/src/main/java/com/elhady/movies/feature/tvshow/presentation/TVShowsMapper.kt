package com.elhady.movies.feature.tvshow.presentation

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.tvshow.TvShows

import javax.inject.Inject

class TVShowsMapper @Inject constructor() :
    Mapper<TvShows, TVShowsUI> {
    override fun map(input: TvShows): TVShowsUI {
        return TVShowsUI(
            tvId = input.id,
            imageUrl = input.imageUrl,
            rate = input.rate
        )
    }
}
