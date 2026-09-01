package com.elhady.movies.feature.details.presentation.tvdetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.feature.details.R
import com.elhady.movies.feature.details.databinding.ItemCommentBinding
import com.elhady.movies.feature.details.databinding.ItemSeasonHorizontalBinding
import com.elhady.movies.feature.details.databinding.TvDetailsItemPeopleRvBinding
import com.elhady.movies.feature.details.databinding.TvDetailsItemRecommendedRvBinding
import com.elhady.movies.core.ui.adapter.PeopleAdapter
import com.elhady.movies.feature.details.databinding.TvDetailsItemInfoBinding
import com.elhady.movies.feature.details.presentation.tvdetails.TvDetailsItem
import com.elhady.movies.feature.details.presentation.tvdetails.listener.TvDetailsListeners

class TvDetailsAdapter(
    private val listener: TvDetailsListeners
) : ListAdapter<TvDetailsItem, RecyclerView.ViewHolder>(TvDetailsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_INFO -> InfoViewHolder(
                DataBindingUtil.inflate(inflater, R.layout.tv_details_item_info, parent, false)
            )
            VIEW_TYPE_PEOPLE -> PeopleViewHolder(
                DataBindingUtil.inflate(inflater, R.layout.tv_details_item_people_rv, parent, false),
                listener
            )
            VIEW_TYPE_SEASONS -> SeasonViewHolder(
                DataBindingUtil.inflate(inflater, R.layout.item_season_horizontal, parent, false)
            )
            VIEW_TYPE_RECOMMENDED -> RecommendedViewHolder(
                DataBindingUtil.inflate(inflater, R.layout.tv_details_item_recommended_rv, parent, false),
                listener
            )
            VIEW_TYPE_REVIEWS -> ReviewViewHolder(
                DataBindingUtil.inflate(inflater, R.layout.item_comment, parent, false)
            )
            else -> throw IllegalStateException("UNKNOWN VIEW TYPE $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is InfoViewHolder -> holder.bind(item as TvDetailsItem.Info, listener)
            is PeopleViewHolder -> holder.bind(item as TvDetailsItem.People, listener)
            is SeasonViewHolder -> holder.bind(item as TvDetailsItem.Season, listener)
            is RecommendedViewHolder -> holder.bind(item as TvDetailsItem.Recommended, listener)
            is ReviewViewHolder -> holder.bind(item as TvDetailsItem.Review)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is TvDetailsItem.Info -> VIEW_TYPE_INFO
            is TvDetailsItem.People -> VIEW_TYPE_PEOPLE
            is TvDetailsItem.Season -> VIEW_TYPE_SEASONS
            is TvDetailsItem.Recommended -> VIEW_TYPE_RECOMMENDED
            is TvDetailsItem.Review -> VIEW_TYPE_REVIEWS
        }
    }

    class InfoViewHolder(val binding: TvDetailsItemInfoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TvDetailsItem.Info, listener: TvDetailsListeners) {
            binding.item = item
            binding.playButtonListener = listener
            binding.rateListener = listener
            binding.executePendingBindings()
        }
    }

    class PeopleViewHolder(val binding: TvDetailsItemPeopleRvBinding, listener: TvDetailsListeners) :
        RecyclerView.ViewHolder(binding.root) {
        val adapter = PeopleAdapter(listener)
        init {
            binding.recyclerViewPeople.adapter = adapter
        }
        fun bind(item: TvDetailsItem.People, listener: TvDetailsListeners) {
            adapter.submitList(item.people)
            binding.listener = listener
            binding.item = item
            binding.executePendingBindings()
        }
    }

    class SeasonViewHolder(val binding: ItemSeasonHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TvDetailsItem.Season, listener: TvDetailsListeners) {
            binding.item = item.season
            binding.listener = listener
            binding.executePendingBindings()
        }
    }

    class RecommendedViewHolder(
        val binding: TvDetailsItemRecommendedRvBinding,
        listener: TvDetailsListeners
    ) : RecyclerView.ViewHolder(binding.root) {
        val adapter = RecommendedAdapter(listener)
        init {
            binding.recyclerViewRecommended.adapter = adapter
        }
        fun bind(item: TvDetailsItem.Recommended, listener: TvDetailsListeners) {
            adapter.submitList(item.recommended)
            binding.listener = listener
            binding.item = item
            binding.executePendingBindings()
        }
    }

    class ReviewViewHolder(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TvDetailsItem.Review) {
            binding.item = item.review
            binding.executePendingBindings()
        }
    }

    class TvDetailsDiffCallback : DiffUtil.ItemCallback<TvDetailsItem>() {
        override fun areItemsTheSame(oldItem: TvDetailsItem, newItem: TvDetailsItem): Boolean {
            return when {
                oldItem is TvDetailsItem.Info && newItem is TvDetailsItem.Info -> true
                oldItem is TvDetailsItem.People && newItem is TvDetailsItem.People -> true
                oldItem is TvDetailsItem.Season && newItem is TvDetailsItem.Season -> 
                    oldItem.season.id == newItem.season.id
                oldItem is TvDetailsItem.Recommended && newItem is TvDetailsItem.Recommended -> true
                oldItem is TvDetailsItem.Review && newItem is TvDetailsItem.Review -> 
                    oldItem.review == newItem.review
                else -> false
            }
        }
        override fun areContentsTheSame(oldItem: TvDetailsItem, newItem: TvDetailsItem): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        private const val VIEW_TYPE_INFO = 0
        private const val VIEW_TYPE_PEOPLE = 1
        private const val VIEW_TYPE_SEASONS = 2
        private const val VIEW_TYPE_RECOMMENDED = 3
        private const val VIEW_TYPE_REVIEWS = 4
    }
}
