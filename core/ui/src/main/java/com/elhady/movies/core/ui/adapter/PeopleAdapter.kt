package com.elhady.movies.core.ui.adapter

import com.elhady.movies.core.ui.BR
import com.elhady.movies.core.ui.base.BaseAdapter
import com.elhady.movies.core.ui.interaction.PeopleAdapterListener
import com.elhady.movies.core.ui.state.PeopleUiState

class PeopleAdapter(
    list: List<PeopleUiState>,
    listener: PeopleAdapterListener
) : BaseAdapter<PeopleUiState>(list, listener) {
    override val layoutID = com.elhady.movies.core.ui.R.layout.item_people
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
