package com.elhady.movies.core.ui.interaction

import com.elhady.movies.core.ui.base.BaseInteractionListener

interface TvShowListener: BaseInteractionListener {
    fun onClickTvShow(id: Int)
}