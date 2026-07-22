package com.elhady.movies.core.common.presentation

import com.elhady.movies.core.common.bases.BaseInteractionListener


interface MediaListener: BaseInteractionListener {
    fun onClickMedia(id: Int)
}
