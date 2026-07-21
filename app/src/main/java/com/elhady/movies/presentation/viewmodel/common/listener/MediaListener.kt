package com.elhady.movies.presentation.viewmodel.common.listener

import com.elhady.movies.core.common.bases.BaseInteractionListener


interface MediaListener: BaseInteractionListener {
    fun onClickMedia(id: Int)
}
