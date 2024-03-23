package com.elhady.movies.presentation.ui.myrated

import androidx.recyclerview.widget.DiffUtil
import com.elhady.movies.R
import com.elhady.movies.core.bases.BasePagingAdapter
import com.elhady.movies.databinding.ItemMovieHorizontalBinding
import com.elhady.movies.presentation.viewmodel.common.model.MovieHorizontalUIState
import com.elhady.movies.presentation.viewmodel.myrated.MyRatedListner

class MyRateAdapter(listener: MyRatedListner) :
    BasePagingAdapter<MovieHorizontalUIState, ItemMovieHorizontalBinding>(Comparator, listener) {

    override val layoutId = R.layout.item_movie_horizontal

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

