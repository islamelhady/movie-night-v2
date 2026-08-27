package com.elhady.movies.feature.details.presentation.seasondetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.elhady.movies.feature.details.BR
import com.elhady.movies.feature.details.R
import com.elhady.movies.core.ui.base.BaseAdapter
import com.elhady.movies.feature.details.databinding.ItemEpisodeHorizontalBinding
import com.elhady.movies.feature.details.databinding.ItemSeasonDetailsHeaderBinding
import com.elhady.movies.feature.details.presentation.episodedetails.EpisodeListener

class SeasonDetailsAdapter (
    private var list: MutableList<SeasonDetailsItem>,
    private val listener: EpisodeListener
): BaseAdapter<SeasonDetailsItem>(list, listener) {
    override val layoutID: Int = 0 // handled in onCreateViewHolder
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when(viewType){
            VIEW_TYPE_OVERVIEW -> {
                OverviewViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                    R.layout.item_season_details_header,parent,false))
            }
            VIEW_TYPE_EPISODE -> {
                EpisodeViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_episode_horizontal,parent,false))
            }
            else -> throw Exception("UNKNOWN VIEW HOLDER")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when(holder){
            is OverviewViewHolder -> bindOverview(holder, position)
            is EpisodeViewHolder -> bindEpisodes(holder, position)
        }
    }

    private fun bindOverview(holder: OverviewViewHolder, position: Int){
        val overview = list[position] as SeasonDetailsItem.Overview
        holder.binding.item = overview
    }

    private fun bindEpisodes(holder: EpisodeViewHolder, position: Int){
        val episode = list[position] as SeasonDetailsItem.Episode
        holder.binding.item = episode.episode
        holder.binding.listener = listener
    }
    override fun setItems(newItems: List<SeasonDetailsItem>) {
        list = newItems.toMutableList()
        super.setItems(newItems)
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is SeasonDetailsItem.Overview -> VIEW_TYPE_OVERVIEW
            is SeasonDetailsItem.Episode -> VIEW_TYPE_EPISODE
        }
    }

    class OverviewViewHolder(val binding: ItemSeasonDetailsHeaderBinding): BaseViewHolder(binding)

    class EpisodeViewHolder(val binding: ItemEpisodeHorizontalBinding): BaseViewHolder(binding)

    companion object {
        private const val VIEW_TYPE_OVERVIEW = 0
        private const val VIEW_TYPE_EPISODE = 1
    }
}
