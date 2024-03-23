package com.elhady.movies.presentation.viewmodel.common.listener

import com.elhady.movies.core.bases.BaseInteractionListener


interface PeopleListener: BaseInteractionListener {
    fun onClickPeople(id: Int)
}