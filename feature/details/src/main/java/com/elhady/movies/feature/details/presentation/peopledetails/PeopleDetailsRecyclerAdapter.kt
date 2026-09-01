package com.elhady.movies.feature.details.presentation.peopledetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.feature.details.BR
import com.elhady.movies.feature.details.R
import com.elhady.movies.feature.details.databinding.ItemPeopleMediaBinding

class PeopleDetailsRecyclerAdapter(
    private val listener: PeopleDetailsListener
) : ListAdapter<PeopleDetailsUiState.PeopleMediaUiState, PeopleDetailsRecyclerAdapter.PeopleDetailsViewHolder>(PeopleDetailsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleDetailsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemPeopleMediaBinding>(
            layoutInflater, R.layout.item_people_media, parent, false
        )
        return PeopleDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PeopleDetailsViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class PeopleDetailsViewHolder(private val binding: ItemPeopleMediaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PeopleDetailsUiState.PeopleMediaUiState, listener: PeopleDetailsListener) {
            binding.setVariable(BR.item, item)
            binding.setVariable(BR.listener, listener)
            binding.executePendingBindings()
        }
    }

    class PeopleDetailsDiffCallback : DiffUtil.ItemCallback<PeopleDetailsUiState.PeopleMediaUiState>() {
        override fun areItemsTheSame(
            oldItem: PeopleDetailsUiState.PeopleMediaUiState,
            newItem: PeopleDetailsUiState.PeopleMediaUiState
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: PeopleDetailsUiState.PeopleMediaUiState,
            newItem: PeopleDetailsUiState.PeopleMediaUiState
        ): Boolean = oldItem == newItem
    }
}
