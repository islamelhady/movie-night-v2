package com.elhady.movies.feature.showmore.presentation

import com.elhady.movies.core.ui.base.BaseInteractionListener
import com.elhady.movies.core.domain.model.account.ListType


interface ShowMoreListener : BaseInteractionListener {
    fun onClickItem(mediaId: Int, type: ListType)
    fun onClickBackNavigate()
}
