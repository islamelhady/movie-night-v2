package com.elhady.movies.core.ui.listener

import com.elhady.movies.core.ui.bases.BaseInteractionListener


interface PeopleListener: BaseInteractionListener {
    fun onClickPeople(id: Int)
}
