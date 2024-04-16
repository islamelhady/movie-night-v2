package com.elhady.movies.presentation.ui.seasondetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseAdapter
import com.elhady.movies.databinding.ItemEpisodeHorizontalBinding
import com.elhady.movies.databinding.ItemSeasonDetailsHeaderBinding
import com.elhady.movies.presentation.viewmodel.common.listener.EpisodeListener

class SeasonDetailsAdapter (
    private var list: MutableList<SeasonDetailsItem>,
    private val listener: EpisodeListener
): BaseAdapter<SeasonDetailsItem>(list, listener) {
    override val layoutID: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when(viewType){
            SeasonDetailsType.OVERVIEW.ordinal -> {
                OverviewViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                    R.layout.item_season_details_header,parent,false))
            }
            SeasonDetailsType.EPISODE.ordinal -> {
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
        val overview = list[position] as SeasonDetailsItem.OverviewItem
        holder.binding.item = overview
    }

    private fun bindEpisodes(holder: EpisodeViewHolder, position: Int){
        val episode = list[position] as SeasonDetailsItem.EpisodeItem
        holder.binding.item = episode.episodeHorizontalUIState
        holder.binding.listener = listener
    }

    override fun setItems(newItems: List<SeasonDetailsItem>) {
        list = newItems.sortedBy { it.type.ordinal }.toMutableList()
        super.setItems(newItems)
    }

    override fun getItemViewType(position: Int): Int = list[position].type.ordinal

    class OverviewViewHolder(val binding: ItemSeasonDetailsHeaderBinding): BaseViewHolder(binding)

    class EpisodeViewHolder(val binding: ItemEpisodeHorizontalBinding): BaseViewHolder(binding)
}