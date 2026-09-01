package com.elhady.movies.feature.tvshow.presentation.tvshow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.feature.tvshow.BR
import com.elhady.movies.feature.tvshow.R
import com.elhady.movies.core.ui.interaction.TvShowAdapterListener
import com.elhady.movies.feature.tvshow.databinding.ItemTvShowBinding
import com.elhady.movies.feature.tvshow.presentation.tvshow.ShowUiState

class TvShowAdapter(
    private val listener: TvShowAdapterListener
) : PagingDataAdapter<ShowUiState, TvShowAdapter.TvShowViewHolder>(Comparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemTvShowBinding>(
            layoutInflater, R.layout.item_tv_show, parent, false
        )
        return TvShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, listener) }
    }

    class TvShowViewHolder(private val binding: ItemTvShowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ShowUiState, listener: TvShowAdapterListener) {
            binding.setVariable(BR.item, item)
            binding.setVariable(BR.listener, listener)
            binding.executePendingBindings()
        }
    }

    object Comparator : DiffUtil.ItemCallback<ShowUiState>() {
        override fun areItemsTheSame(oldItem: ShowUiState, newItem: ShowUiState): Boolean {
            return oldItem.tvId == newItem.tvId
        }

        override fun areContentsTheSame(oldItem: ShowUiState, newItem: ShowUiState): Boolean {
            return oldItem == newItem
        }
    }
}
