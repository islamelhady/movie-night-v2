package com.elhady.ui.reviews

import com.elhady.base.BaseAdapter
import com.elhady.base.BaseInteractionListener
import com.elhady.ui.R
import com.elhady.viewmodel.models.ReviewUiState


class ReviewAdapter(items: List<ReviewUiState>, listener: BaseInteractionListener) :
    BaseAdapter<ReviewUiState>(items, listener) {
    override val layoutID: Int = R.layout.item_review
}