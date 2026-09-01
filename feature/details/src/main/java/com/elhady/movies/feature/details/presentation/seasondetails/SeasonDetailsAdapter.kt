package com.elhady.movies.feature.details.presentation.seasondetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.feature.details.R
import com.elhady.movies.feature.details.databinding.ItemEpisodeHorizontalBinding
import com.elhady.movies.feature.details.databinding.ItemSeasonDetailsHeaderBinding
import com.elhady.movies.feature.details.presentation.episodedetails.EpisodeListener

class SeasonDetailsAdapter(
    private val listener: EpisodeListener
) : ListAdapter<SeasonDetailsItem, RecyclerView.ViewHolder>(SeasonDetailsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_OVERVIEW -> {
                OverviewViewHolder(
                    DataBindingUtil.inflate(inflater, R.layout.item_season_details_header, parent, false)
                )
            }
            VIEW_TYPE_EPISODE -> {
                EpisodeViewHolder(
                    DataBindingUtil.inflate(inflater, R.layout.item_episode_horizontal, parent, false)
                )
            }
            else -> throw Exception("UNKNOWN VIEW HOLDER")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is OverviewViewHolder -> holder.bind(item as SeasonDetailsItem.Overview)
            is EpisodeViewHolder -> holder.bind(item as SeasonDetailsItem.Episode, listener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SeasonDetailsItem.Overview -> VIEW_TYPE_OVERVIEW
            is SeasonDetailsItem.Episode -> VIEW_TYPE_EPISODE
        }
    }

    class OverviewViewHolder(val binding: ItemSeasonDetailsHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SeasonDetailsItem.Overview) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    class EpisodeViewHolder(val binding: ItemEpisodeHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SeasonDetailsItem.Episode, listener: EpisodeListener) {
            binding.item = item.episode
            binding.listener = listener
            binding.executePendingBindings()
        }
    }

    class SeasonDetailsDiffCallback : DiffUtil.ItemCallback<SeasonDetailsItem>() {
        override fun areItemsTheSame(oldItem: SeasonDetailsItem, newItem: SeasonDetailsItem): Boolean {
            return when {
                oldItem is SeasonDetailsItem.Overview && newItem is SeasonDetailsItem.Overview -> true
                oldItem is SeasonDetailsItem.Episode && newItem is SeasonDetailsItem.Episode -> 
                    oldItem.episode.id == newItem.episode.id
                else -> false
            }
        }
        override fun areContentsTheSame(oldItem: SeasonDetailsItem, newItem: SeasonDetailsItem): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        private const val VIEW_TYPE_OVERVIEW = 0
        private const val VIEW_TYPE_EPISODE = 1
    }
}
