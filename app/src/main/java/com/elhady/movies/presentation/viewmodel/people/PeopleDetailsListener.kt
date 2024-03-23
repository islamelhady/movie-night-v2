package com.elhady.movies.presentation.viewmodel.people

import com.elhady.movies.core.bases.BaseInteractionListener


interface PeopleDetailsListener : BaseInteractionListener {
    fun onClickMedia(itemId: Int,name:String)
    fun backNavigate()

}