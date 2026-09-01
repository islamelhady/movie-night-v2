package com.elhady.movies.feature.details.presentation.peopledetails

import com.elhady.movies.core.common.MediaType
import com.elhady.movies.core.ui.base.BaseInteractionListener

interface PeopleDetailsListener : BaseInteractionListener {

    fun onClickMedia(
        itemId: Int,
        type: MediaType,
    )

    fun backNavigate()

    fun onClickRetry()
}
