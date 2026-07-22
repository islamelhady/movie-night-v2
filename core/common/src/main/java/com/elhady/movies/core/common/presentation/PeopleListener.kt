package com.elhady.movies.core.common.presentation

import com.elhady.movies.core.common.bases.BaseInteractionListener


interface PeopleListener: BaseInteractionListener {
    fun onClickPeople(id: Int)
}
