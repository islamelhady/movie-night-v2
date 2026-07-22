package com.elhady.movies.feature.details.presentation.peopledetails

import com.elhady.movies.core.common.bases.BaseInteractionListener


interface PeopleDetailsListener : BaseInteractionListener {
    fun onClickMedia(itemId: Int, type: String)
    fun backNavigate()

}
