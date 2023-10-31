package com.elhady.movies.ui.reviews

import com.elhady.movies.R
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener
import com.elhady.movies.ui.models.ReviewUiState

class ReviewAdapter(items: List<ReviewUiState>, listener: BaseInteractionListener) :
    BaseAdapter<ReviewUiState>(items, listener) {
    override val layoutID: Int = R.layout.item_movie_review
}