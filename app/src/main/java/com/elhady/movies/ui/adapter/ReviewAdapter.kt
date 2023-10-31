package com.elhady.movies.ui.adapter

import com.elhady.movies.R
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener
import com.elhady.movies.ui.models.ReviewUiState


class ReviewAdapter(items: List<ReviewUiState>,  listener: ReviewInteractionListener) :
    BaseAdapter<ReviewUiState>(items, listener) {
    override val layoutID: Int = R.layout.item_movie_review
}

interface ReviewInteractionListener : BaseInteractionListener {
    fun onClickMedia(mediaId: Int)
}