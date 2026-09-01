package com.elhady.movies.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.core.ui.BR
import com.elhady.movies.core.ui.R
import com.elhady.movies.core.ui.databinding.ItemPeopleBinding
import com.elhady.movies.core.ui.interaction.PeopleAdapterListener
import com.elhady.movies.core.ui.state.PeopleUiState

class PeopleAdapter(
    private val listener: PeopleAdapterListener
) : ListAdapter<PeopleUiState, PeopleAdapter.PeopleViewHolder>(PeopleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemPeopleBinding>(
            layoutInflater, R.layout.item_people, parent, false
        )
        return PeopleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class PeopleViewHolder(private val binding: ItemPeopleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PeopleUiState, listener: PeopleAdapterListener) {
            binding.setVariable(BR.item, item)
            binding.setVariable(BR.listener, listener)
            binding.executePendingBindings()
        }
    }

    class PeopleDiffCallback : DiffUtil.ItemCallback<PeopleUiState>() {
        override fun areItemsTheSame(
            oldItem: PeopleUiState,
            newItem: PeopleUiState
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: PeopleUiState,
            newItem: PeopleUiState
        ): Boolean = oldItem == newItem
    }
}
