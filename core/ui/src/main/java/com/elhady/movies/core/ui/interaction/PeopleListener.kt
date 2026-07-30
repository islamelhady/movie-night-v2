package com.elhady.movies.core.ui.interaction

import com.elhady.movies.core.ui.base.BaseInteractionListener


interface PeopleListener: BaseInteractionListener {
    fun onClickPeople(id: Int)
}
