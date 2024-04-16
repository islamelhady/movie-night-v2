package com.elhady.movies.presentation.viewmodel.showmore

import com.elhady.movies.core.bases.BaseInteractionListener
import com.elhady.movies.core.bases.ListType


interface ShowMoreListener : BaseInteractionListener {
    fun onClickItem(mediaId: Int, type: ListType)
    fun onClickBackNavigate()
}
