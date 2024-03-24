package com.elhady.movies.presentation.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseAdapter
import com.elhady.movies.databinding.ItemMovieHorizontalBinding
import com.elhady.movies.databinding.SearchItemPeopleBinding
import com.elhady.movies.presentation.viewmodel.search.SearchItem
import com.elhady.movies.presentation.viewmodel.search.SearchItemType
import com.elhady.movies.presentation.viewmodel.search.SearchListener

class SearchAdapter(
    private var list: MutableList<SearchItem>,
    private val listener: SearchListener
) : BaseAdapter<SearchItem>(list, listener) {
    override val layoutID: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            SearchItemType.MEDIA.ordinal -> {
                MediaViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_movie_horizontal, parent, false
                    )
                )
            }

            SearchItemType.PEOPLE.ordinal -> {
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
        val media = list[position] as SearchItem.MediaItem
        holder.binding.item = media.movieHorizontalUIState
        holder.binding.listener = listener

    }

    private fun bindPeople(holder: PeopleViewHolder, position: Int) {
        val people = list[position] as SearchItem.PeopleItem
        holder.binding.item = people.peopleItem
        holder.binding.listener = listener
    }

//    fun setItem(item: SearchItem) {
//        val newItems = list.apply {
//            removeAt(item.type.ordinal)
//            add(item.type.ordinal, item)
//        }
//        setItems(newItems)
//    }

    override fun setItems(newItems: List<SearchItem>) {
        list = newItems.sortedBy { it.type.ordinal }.toMutableList()
        super.setItems(newItems)
    }


    override fun getItemViewType(position: Int): Int = list[position].type.ordinal

    class MediaViewHolder(val binding: ItemMovieHorizontalBinding) : BaseViewHolder(binding)

    class PeopleViewHolder(val binding: SearchItemPeopleBinding) : BaseViewHolder(binding)

}