package com.elhady.movies.presentation.viewmodel.peopledetails

import com.elhady.movies.core.bases.BaseInteractionListener


interface PeopleDetailsListener : BaseInteractionListener {
    fun onClickMedia(itemId: Int, type: String)
    fun backNavigate()

}