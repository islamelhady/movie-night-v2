package com.elhady.movies.core.ui.interaction

import com.elhady.movies.core.ui.base.BaseInteractionListener


interface MovieAdapterListener: BaseInteractionListener {
    fun onClickMovie(id: Int)
}
