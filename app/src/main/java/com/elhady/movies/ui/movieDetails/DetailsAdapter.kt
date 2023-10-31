package com.elhady.movies.ui.movieDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import com.elhady.movies.R
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener
import com.elhady.movies.ui.home.adapters.ActorAdapter
import com.elhady.movies.ui.home.adapters.ActorInteractionListener
import com.elhady.movies.ui.home.adapters.MovieAdapter
import com.elhady.movies.ui.home.adapters.MovieInteractionListener

class DetailsAdapter(
    private var detailsItems: List<DetailsItem>,
    val listener: BaseInteractionListener
) : BaseAdapter<DetailsItem>(detailsItems, listener) {
    override val layoutID: Int = 0

    override fun setItems(newItems: List<DetailsItem>) {
        detailsItems = newItems.sortedBy {
            it.priority
        }.toMutableList()
        super.setItems(newItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                viewType,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        bind(holder as ItemViewHolder, position)
    }

    private fun bind(holder: ItemViewHolder, position: Int){
        when (val currentItem = detailsItems[position]){
            is DetailsItem.Header -> {
                holder.binding.setVariable(BR.item, currentItem.data)
                holder.binding.setVariable(BR.listener, listener as DetailsInteractionListener)
            }

            is DetailsItem.Cast -> {
                holder.binding.setVariable(BR.adapterRecycler, ActorAdapter(items = currentItem.data, listener = listener as ActorInteractionListener, R.layout.item_cast))
            }

            is DetailsItem.Similar -> {
                holder.binding.setVariable(BR.adapterRecycler, MovieAdapter(items = currentItem.data, listener = listener as MovieInteractionListener))
            }

            is DetailsItem.Reviews -> {
                holder.binding.setVariable(BR.item, currentItem.data)
            }

            DetailsItem.SeeAllReviewsButton -> holder.binding.setVariable(BR.listener, listener as DetailsInteractionListener)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when(detailsItems[position]){
            is DetailsItem.Header -> R.layout.item_movie_details
            is DetailsItem.Cast -> R.layout.list_cast
            is DetailsItem.Similar -> R.layout.list_similar_movies
            is DetailsItem.Reviews -> R.layout.item_movie_review
            DetailsItem.SeeAllReviewsButton -> R.layout.item_see_all_reviews
        }

    }


}