package com.elhady.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


interface BaseInteractionListener
abstract class BaseAdapter<T>(
    private var items: List<T>,
    private val listener: BaseInteractionListener
): RecyclerView.Adapter<BaseAdapter.BaseViewHolder>() {

    protected abstract val layoutID: Int

    open fun setItems(newItems: List<T>) {
        val diffUtil = DiffUtil.calculateDiff(
            BaseDiffUtil(
                items,
                newItems,
                { oldItem, newItem -> areItemSame(oldItem, newItem) },
                { oldItem, newItem -> areItemContent(oldItem, newItem) })
        )
        items = newItems
        diffUtil.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = items.size

    open fun areItemSame(oldItem: T, newItem: T): Boolean {
        return oldItem?.equals(newItem) == true
    }

    open fun areItemContent(oldItem: T, newItem: T) = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        ItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutID,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder is ItemViewHolder) bind(holder, position)
    }

    private fun bind(holder: ItemViewHolder, position: Int) {
        holder.binding.apply {
            setVariable(BR.item, items[position])
            setVariable(BR.listener, listener)
        }
    }


    class ItemViewHolder(val binding: ViewDataBinding) : BaseViewHolder(binding)

    abstract class BaseViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
}