package com.elhady.movies.core.ui.listener

import com.elhady.movies.core.ui.bases.BaseInteractionListener


interface MovieListener: BaseInteractionListener {
    fun onClickMedia(id: Int)
}
