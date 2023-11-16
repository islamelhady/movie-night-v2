package com.elhady.movies.ui.profile.ratings

import com.elhady.movies.R
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener

class MyRatingAdapter(items: List<RatedUiState>, listener: MyRatingInteractionListener) :
    BaseAdapter<RatedUiState>(items, listener) {
    override val layoutID: Int = R.layout.item_rating
}

interface MyRatingInteractionListener : BaseInteractionListener {
    fun onClickRating()
}