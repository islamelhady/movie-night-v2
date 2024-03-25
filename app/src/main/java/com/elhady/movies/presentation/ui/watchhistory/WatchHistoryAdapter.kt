package com.elhady.movies.presentation.ui.watchhistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseAdapter
import com.elhady.movies.databinding.WatchHistoryRecyclerViewCardBinding
import com.elhady.movies.databinding.WatchHistoryRecyclerViewTitleBinding
import com.elhady.movies.presentation.viewmodel.common.listener.MediaListener

class WatchHistoryAdapter(
    private var items: List<WatchHistoryRecyclerItem>,
    private val listener: MediaListener,
) : BaseAdapter<WatchHistoryRecyclerItem>(items, listener) {
    override val layoutID = -1

    class TitleViewHolder(val binding: WatchHistoryRecyclerViewTitleBinding) :
        BaseViewHolder(binding)

    class CardViewHolder(val binding: WatchHistoryRecyclerViewCardBinding) :
        BaseViewHolder(binding)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            TITLE_ITEM -> TitleViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.watch_history_recycler_view_title,
                    parent,
                    false
                )
            )

            CARD_ITEM -> CardViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.watch_history_recycler_view_card,
                    parent,
                    false
                )

            )

            else -> throw Exception("item not found")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> bindTitle(holder, items[position])
            is CardViewHolder -> bindCard(holder, items[position])
            else -> throw Exception("recycler item not found")
        }
    }


    private fun bindCard(holder: CardViewHolder, item: WatchHistoryRecyclerItem) {
        holder.binding.item = (item as WatchHistoryRecyclerItem.MovieCard).movie
        holder.binding.listener = listener
    }

    private fun bindTitle(holder: TitleViewHolder, item: WatchHistoryRecyclerItem) {
        holder.binding.item = (item as WatchHistoryRecyclerItem.Title).title
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is WatchHistoryRecyclerItem.Title -> TITLE_ITEM
            is WatchHistoryRecyclerItem.MovieCard -> CARD_ITEM
        }
    }


    override fun setItems(newItems: List<WatchHistoryRecyclerItem>) {
        items = newItems
        super.setItems(newItems)
    }


    private companion object {
        const val TITLE_ITEM = 21
        const val CARD_ITEM = 14
    }

}