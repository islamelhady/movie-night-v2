package com.elhady.movies.feature.search.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.elhady.movies.feature.search.BR
import com.elhady.movies.feature.search.R
import com.elhady.movies.core.ui.base.BaseAdapter
import com.elhady.movies.core.ui.databinding.ItemMovieHorizontalBinding
import com.elhady.movies.feature.search.databinding.SearchItemPeopleBinding
import com.elhady.movies.feature.search.presentation.search.SearchItem
import com.elhady.movies.feature.search.presentation.search.SearchAdapterListener

class SearchAdapter(
    list: MutableList<SearchItem>,
    private val listener: SearchAdapterListener
) : BaseAdapter<SearchItem>(list, listener) {
    override val layoutID: Int = 0 // handled in onCreateViewHolder
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_MEDIA -> {
                MediaViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        com.elhady.movies.core.ui.R.layout.item_movie_horizontal, parent, false
                    )
                )
            }

            VIEW_TYPE_PEOPLE -> {
                PeopleViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.search_item_people, parent, false
                    )
                )
            }

            else -> throw Exception("UNKNOWN VIEW HOLDER")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder) {
            is MediaViewHolder -> bindMedia(holder, position)
            is PeopleViewHolder -> bindPeople(holder, position)
        }
    }

    private fun bindMedia(holder: MediaViewHolder, position: Int) {
        val media = getItems()[position] as SearchItem.MediaItem
        holder.binding.item = media.movieHorizontalUiState
        holder.binding.listener = listener

    }

    private fun bindPeople(holder: PeopleViewHolder, position: Int) {
        val people = getItems()[position] as SearchItem.PeopleItem
        holder.binding.item = people.peopleItem
        holder.binding.listener = listener
    }

    override fun setItems(newItems: List<SearchItem>) {
        val sortedItems = newItems.sortedBy {
            when (it) {
                is SearchItem.MediaItem -> VIEW_TYPE_MEDIA
                is SearchItem.PeopleItem -> VIEW_TYPE_PEOPLE
            }
        }
        super.setItems(sortedItems)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItems()[position]) {
            is SearchItem.MediaItem -> VIEW_TYPE_MEDIA
            is SearchItem.PeopleItem -> VIEW_TYPE_PEOPLE
        }
    }

    class MediaViewHolder(val binding: ItemMovieHorizontalBinding) : BaseViewHolder(binding)

    class PeopleViewHolder(val binding: SearchItemPeopleBinding) : BaseViewHolder(binding)

    companion object {
        private const val VIEW_TYPE_MEDIA = 0
        private const val VIEW_TYPE_PEOPLE = 1
    }

}
