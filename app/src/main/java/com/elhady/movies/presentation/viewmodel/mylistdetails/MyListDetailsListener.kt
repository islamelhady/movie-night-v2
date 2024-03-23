package com.elhady.movies.presentation.viewmodel.mylistdetails

import com.elhady.movies.core.bases.BaseInteractionListener


interface MyListDetailsListener : BaseInteractionListener {
    fun onClickItem(itemId: Int , mediaType: String)

    fun onClickBackButton()
}