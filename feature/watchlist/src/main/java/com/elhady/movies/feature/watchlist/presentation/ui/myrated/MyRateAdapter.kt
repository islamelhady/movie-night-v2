package com.elhady.movies.feature.watchlist.presentation.ui.myrated

import androidx.recyclerview.widget.DiffUtil
import com.elhady.movies.feature.watchlist.BR
import com.elhady.movies.core.ui.base.BasePagingAdapter
import com.elhady.movies.core.ui.databinding.ItemMovieHorizontalBinding
import com.elhady.movies.core.ui.model.MovieHorizontalUIState
import com.elhady.movies.feature.watchlist.presentation.myrated.MyRatedListner

class MyRateAdapter(listener: MyRatedListner) :
    BasePagingAdapter<MovieHorizontalUIState, ItemMovieHorizontalBinding>(Comparator, listener) {

    override val layoutId = com.elhady.movies.core.ui.R.layout.item_movie_horizontal
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener

    object Comparator : DiffUtil.ItemCallback<MovieHorizontalUIState>() {
        override fun areItemsTheSame(oldItem: MovieHorizontalUIState, newItem: MovieHorizontalUIState): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MovieHorizontalUIState,
            newItem: MovieHorizontalUIState
        ): Boolean {
            return oldItem == newItem
        }
    }
}
