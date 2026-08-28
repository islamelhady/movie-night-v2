package com.elhady.movies.feature.showmore.presentation.showmore

import com.elhady.movies.core.domain.model.account.ListType
import com.elhady.movies.core.ui.base.BaseInteractionListener

interface ShowMoreAdapterListener: BaseInteractionListener {
    fun onClickItem(mediaId: Int, type: ListType)
}