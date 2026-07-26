package com.elhady.movies.feature.showmore.presentation

import com.elhady.movies.core.ui.bases.BaseInteractionListener
import com.elhady.movies.core.domain.model.ListType


interface ShowMoreListener : BaseInteractionListener {
    fun onClickItem(mediaId: Int, type: ListType)
    fun onClickBackNavigate()
}
