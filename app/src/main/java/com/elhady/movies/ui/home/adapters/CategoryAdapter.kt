package com.elhady.movies.ui.home.adapters

import com.elhady.movies.R
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.HorizontalBaseAdapter
import com.elhady.movies.ui.home.HomeViewModel

class CategoryAdapter(private val items: List<String>) : BaseAdapter<String>(items) {
    override val layoutID: Int = R.layout.item_category

    override fun areItemContent(oldItem: String, newItem: String): Boolean {
        return true
    }
}

class HorizontalCategoryAdapter(adapter: CategoryAdapter, viewModel: HomeViewModel) :
    HorizontalBaseAdapter<String>(adapter, viewModel)