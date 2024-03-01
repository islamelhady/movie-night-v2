package com.elhady.ui.profile.ratings

import com.elhady.base.BaseAdapter
import com.elhady.base.BaseInteractionListener
import com.elhady.ui.R
import com.elhady.viewmodel.profile.ratings.RatedUiState


class MyRatingAdapter(items: List<RatedUiState>, listener: MyRatingInteractionListener) :
    BaseAdapter<RatedUiState>(items, listener) {
    override val layoutID: Int = R.layout.item_my_rated
}

interface MyRatingInteractionListener : BaseInteractionListener {
    fun onClickRating(rated: RatedUiState)
}