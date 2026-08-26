package com.elhady.movies.core.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


/**
 * A base class for all RecyclerView adapters in the project.
 *
 * @param T The type of items in the adapter.
 * @property items The list of items to be displayed.
 * @property listener The interaction listener for the items.
 */
abstract class BaseAdapter<T>(
    private var items: List<T>,
    private val listener: BaseInteractionListener,
) : RecyclerView.Adapter<BaseAdapter.BaseViewHolder>() {


    abstract val layoutID: Int
    abstract val itemVariableId: Int
    abstract val listenerVariableId: Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        ItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), layoutID, parent, false
            )
        )

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder is ItemViewHolder) bind(holder, position)
    }

    open fun bind(holder: ItemViewHolder, position: Int) {
        holder.binding.apply {
            setVariable(itemVariableId, items[position])
            setVariable(listenerVariableId, listener)
        }
    }

    class ItemViewHolder(val binding: ViewDataBinding) : BaseViewHolder(binding)

    abstract class BaseViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount() = items.size

    open  fun setItems(newItems: List<T>) {
        val diffResult = DiffUtil.calculateDiff(BaseDiffUtil(items, newItems,::areItemsSame, ::areContentSame))
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    open fun areItemsSame(oldItem: T, newItem: T): Boolean {
        return oldItem?.equals(newItem) == true
    }
    open fun areContentSame(oldItem: T, newItem: T) = oldItem == newItem

    class BaseDiffUtil<T>(
        private val oldList: List<T>,
        private val newList: List<T>,
        private val checkIfSameItem: (oldItem: T, newItem: T) -> Boolean,
        private val checkIfSameContent: (oldItem: T, newItem: T) -> Boolean
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return checkIfSameItem(oldList[oldItemPosition], newList[newItemPosition])
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return checkIfSameContent(oldList[oldItemPosition], newList[newItemPosition])
        }

    }

}
