package com.elhady.movies.feature.home.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.feature.home.BR
import com.elhady.movies.feature.home.R
import com.elhady.movies.feature.home.databinding.HomeItemPopularPeopleBinding
import com.elhady.movies.feature.home.presentation.home.HomeAdapterListener
import com.elhady.movies.feature.home.presentation.home.PopularPeopleUiState

class PopularPeopleAdapter(
    private val listener: HomeAdapterListener
) : ListAdapter<PopularPeopleUiState, PopularPeopleAdapter.PopularPeopleViewHolder>(PopularPeopleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularPeopleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<HomeItemPopularPeopleBinding>(
            layoutInflater, R.layout.home_item_popular_people, parent, false
        )
        return PopularPeopleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularPeopleViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class PopularPeopleViewHolder(private val binding: HomeItemPopularPeopleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PopularPeopleUiState, listener: HomeAdapterListener) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()
        }
    }

    class PopularPeopleDiffCallback : DiffUtil.ItemCallback<PopularPeopleUiState>() {
        override fun areItemsTheSame(
            oldItem: PopularPeopleUiState,
            newItem: PopularPeopleUiState
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: PopularPeopleUiState,
            newItem: PopularPeopleUiState
        ): Boolean = oldItem == newItem
    }
}
