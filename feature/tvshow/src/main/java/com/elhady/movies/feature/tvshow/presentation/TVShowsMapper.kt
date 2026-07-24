package com.elhady.movies.feature.tvshow.presentation

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.TVShowsEntity

import javax.inject.Inject

class TVShowsMapper @Inject constructor() :
    Mapper<TVShowsEntity, TVShowsUI> {
    override fun map(input: TVShowsEntity): TVShowsUI {
        return TVShowsUI(
            tvId = input.id,
            imageUrl = input.imageUrl,
            rate = input.rate
        )
    }
}
