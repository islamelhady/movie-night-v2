package com.elhady.movies.core.ui.listener

import com.elhady.movies.core.ui.base.BaseInteractionListener


interface MovieListener: BaseInteractionListener {
    fun onClickMedia(id: Int)
}
