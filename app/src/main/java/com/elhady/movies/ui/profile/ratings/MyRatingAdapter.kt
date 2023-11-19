package com.elhady.movies.ui.profile.ratings

import com.elhady.movies.R
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener

class MyRatingAdapter(items: List<RatedUiState>, listener: MyRatingInteractionListener) :
    BaseAdapter<RatedUiState>(items, listener) {
    override val layoutID: Int = R.layout.item_my_rated
}

interface MyRatingInteractionListener : BaseInteractionListener {
    fun onClickRating(rated: RatedUiState)
}