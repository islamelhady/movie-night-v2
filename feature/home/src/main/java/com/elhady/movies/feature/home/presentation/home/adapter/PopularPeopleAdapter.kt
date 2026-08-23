package com.elhady.movies.feature.home.presentation.home.adapter

import com.elhady.movies.core.ui.base.BaseAdapter
import com.elhady.movies.core.ui.interaction.PeopleAdapterListener
import com.elhady.movies.feature.home.BR
import com.elhady.movies.feature.home.R
import com.elhady.movies.feature.home.presentation.home.PopularPersonUiState

class PopularPeopleAdapter(
    itemsPopularPeople: List<PopularPersonUiState>,
    listener: PeopleAdapterListener
) : BaseAdapter<PopularPersonUiState>(itemsPopularPeople, listener) {
    override val layoutID = R.layout.home_item_popular_people
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener

}
