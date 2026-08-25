package com.elhady.movies.feature.watchlist.presentation.ratedmedia.adapter

import androidx.recyclerview.widget.DiffUtil
import com.elhady.movies.core.ui.base.BasePagingAdapter
import com.elhady.movies.core.ui.databinding.ItemMovieHorizontalBinding
import com.elhady.movies.core.ui.interaction.MovieAdapterListener
import com.elhady.movies.core.ui.state.MovieHorizontalUiState
import com.elhady.movies.feature.watchlist.BR

class RatedMediaAdapter(listener: MovieAdapterListener) :
    BasePagingAdapter<MovieHorizontalUiState, ItemMovieHorizontalBinding>(Comparator, listener) {

    override val layoutId = com.elhady.movies.core.ui.R.layout.item_movie_horizontal
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener

    object Comparator : DiffUtil.ItemCallback<MovieHorizontalUiState>() {
        override fun areItemsTheSame(oldItem: MovieHorizontalUiState, newItem: MovieHorizontalUiState): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MovieHorizontalUiState,
            newItem: MovieHorizontalUiState
        ): Boolean {
            return oldItem == newItem
        }
    }
}
