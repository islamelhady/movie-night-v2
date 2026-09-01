package com.elhady.movies.feature.details.presentation.moviedetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.feature.details.R
import com.elhady.movies.feature.details.databinding.ItemReviewBinding
import com.elhady.movies.feature.details.databinding.MovieDetailsItemPopularPeopleBinding
import com.elhady.movies.feature.details.databinding.MovieDetailsItemRecommendedBinding
import com.elhady.movies.feature.details.databinding.MovieDetailsItemUpperBinding
import com.elhady.movies.core.ui.adapter.MediaVerticalAdapter
import com.elhady.movies.core.ui.adapter.PeopleAdapter
import com.elhady.movies.core.ui.interaction.MediaListener
import com.elhady.movies.core.ui.interaction.PeopleAdapterListener
import com.elhady.movies.feature.details.presentation.moviedetails.MovieDetailsListener

class MovieDetailsAdapter(
    private val listener: MovieDetailsListener,
    private val movieListener: MediaListener,
    private val peopleAdapterListener: PeopleAdapterListener,
) : ListAdapter<MovieDetailsItem, RecyclerView.ViewHolder>(MovieDetailsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_UPPER -> {
                UpperViewHolder(
                    DataBindingUtil.inflate(inflater, R.layout.movie_details_item_upper, parent, false)
                )
            }
            VIEW_TYPE_PEOPLE -> {
                PeopleViewHolder(
                    DataBindingUtil.inflate(inflater, R.layout.movie_details_item_popular_people, parent, false),
                    peopleAdapterListener
                )
            }
            VIEW_TYPE_RECOMMENDED -> {
                RecommendedViewHolder(
                    DataBindingUtil.inflate(inflater, R.layout.movie_details_item_recommended, parent, false),
                    movieListener
                )
            }
            VIEW_TYPE_REVIEWS -> {
                ReviewsViewHolder(
                    DataBindingUtil.inflate(inflater, R.layout.item_review, parent, false)
                )
            }
            else -> throw Exception("UNKNOWN VIEW TYPE")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is UpperViewHolder -> holder.bind(item as MovieDetailsItem.Upper, listener)
            is PeopleViewHolder -> holder.bind(item as MovieDetailsItem.People)
            is RecommendedViewHolder -> holder.bind(item as MovieDetailsItem.Recommended, listener)
            is ReviewsViewHolder -> holder.bind(item as MovieDetailsItem.Reviews)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MovieDetailsItem.Upper -> VIEW_TYPE_UPPER
            is MovieDetailsItem.People -> VIEW_TYPE_PEOPLE
            is MovieDetailsItem.Recommended -> VIEW_TYPE_RECOMMENDED
            is MovieDetailsItem.Reviews -> VIEW_TYPE_REVIEWS
        }
    }

    class UpperViewHolder(val binding: MovieDetailsItemUpperBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieDetailsItem.Upper, listener: MovieDetailsListener) {
            binding.item = item
            binding.listener = listener
            binding.executePendingBindings()
        }
    }

    class PeopleViewHolder(
        val binding: MovieDetailsItemPopularPeopleBinding,
        listener: PeopleAdapterListener
    ) : RecyclerView.ViewHolder(binding.root) {
        val adapter = PeopleAdapter(listener)
        init {
            binding.recyclerViewPeople.adapter = adapter
        }
        fun bind(item: MovieDetailsItem.People) {
            adapter.submitList(item.list)
            binding.item = item
            binding.executePendingBindings()
        }
    }

    class RecommendedViewHolder(val binding: MovieDetailsItemRecommendedBinding, listener: MediaListener) :
        RecyclerView.ViewHolder(binding.root) {
        val adapter = MediaVerticalAdapter(listener)
        init {
            binding.recyclerViewRecommened.adapter = adapter
        }
        fun bind(item: MovieDetailsItem.Recommended, listener: MovieDetailsListener) {
            adapter.submitList(item.list)
            binding.item = item
            binding.listener = listener
            binding.executePendingBindings()
        }
    }

    class ReviewsViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieDetailsItem.Reviews) {
            binding.item = item.list
            binding.executePendingBindings()
        }
    }

    class MovieDetailsDiffCallback : DiffUtil.ItemCallback<MovieDetailsItem>() {
        override fun areItemsTheSame(oldItem: MovieDetailsItem, newItem: MovieDetailsItem): Boolean {
            return when {
                oldItem is MovieDetailsItem.Upper && newItem is MovieDetailsItem.Upper -> true
                oldItem is MovieDetailsItem.People && newItem is MovieDetailsItem.People -> true
                oldItem is MovieDetailsItem.Recommended && newItem is MovieDetailsItem.Recommended -> true
                oldItem is MovieDetailsItem.Reviews && newItem is MovieDetailsItem.Reviews -> true
                else -> false
            }
        }
        override fun areContentsTheSame(oldItem: MovieDetailsItem, newItem: MovieDetailsItem): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        private const val VIEW_TYPE_UPPER = 0
        private const val VIEW_TYPE_PEOPLE = 1
        private const val VIEW_TYPE_RECOMMENDED = 2
        private const val VIEW_TYPE_REVIEWS = 3
    }
}
