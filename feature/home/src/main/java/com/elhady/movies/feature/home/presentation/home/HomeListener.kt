package com.elhady.movies.feature.home.presentation.home

import com.elhady.movies.core.common.ShowMoreType

interface HomeListener {
    fun onClickShowMore(type: ShowMoreType)

    fun onClickRetry()
}
