package com.elhady.movies.core.common.presentation

import com.elhady.movies.core.common.bases.BaseInteractionListener


interface MovieListener: BaseInteractionListener {
    fun onClickMedia(id: Int)
}
