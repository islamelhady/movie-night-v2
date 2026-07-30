package com.elhady.movies.core.ui.interaction

import com.elhady.movies.core.ui.base.BaseInteractionListener


interface MediaListener: BaseInteractionListener {
    fun onClickMedia(id: Int)
}
