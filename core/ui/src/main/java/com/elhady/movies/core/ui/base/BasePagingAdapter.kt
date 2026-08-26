package com.elhady.movies.core.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * A base class for all PagingDataAdapter instances in the project that use Data Binding.
 *
 * @param T The type of items in the adapter.
 * @param VB The type of the ViewDataBinding associated with the item's layout.
 * @param diffCallback The callback to determine item differences.
 * @param listener The interaction listener for the items.
 */
abstract class BasePagingAdapter<T : Any, VB : ViewDataBinding>(
    diffCallback: DiffUtil.ItemCallback<T>,
    private val listener: BaseInteractionListener
) : PagingDataAdapter<T, BasePagingAdapter<T, VB>.BaseViewHolder>(diffCallback) {

    @get:LayoutRes
    abstract val layoutId: Int
    abstract val itemVariableId: Int
    abstract val listenerVariableId: Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<VB>(
            inflater,
            layoutId,
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            getItem(position)?.let { item ->
                holder.bind(item)
            }
        }
    }

    inner class ItemViewHolder(private val binding: VB) :
        BaseViewHolder(binding) {

        fun bind(item: T) {
            binding.apply {
                setVariable(itemVariableId, item)
                setVariable(listenerVariableId, listener)
                executePendingBindings()
            }
        }
    }

    abstract inner class BaseViewHolder(item: ViewDataBinding) : RecyclerView.ViewHolder(item.root)
}
