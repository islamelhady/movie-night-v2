package com.elhady.movies.ui.home.adapters

import com.elhady.movies.R
import com.elhady.movies.data.Category
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.HorizontalBaseAdapter
import com.elhady.movies.ui.home.HomeViewModel

class CategoryAdapter(private val items: List<Category>) : BaseAdapter<Category>(items) {
    override val layoutID: Int = R.layout.item_category
    override fun areItemContent(oldItem: Category, newItem: Category): Boolean {
        return true
    }


}

class HorizontalCategoryAdapter(adapter: CategoryAdapter, viewModel: HomeViewModel) :
    HorizontalBaseAdapter<BaseAdapter<Category>,HomeViewModel>(adapter, viewModel) {
    override val layoutID: Int = R.layout.recycler_horizontal
}