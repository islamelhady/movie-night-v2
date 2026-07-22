package com.elhady.movies.presentation.ui.common.adapters

import com.elhady.movies.BR
import com.elhady.movies.R
import com.elhady.movies.core.common.bases.BaseAdapter
import com.elhady.movies.core.common.presentation.PeopleListener
import com.elhady.movies.core.common.presentation.model.PeopleUIState

class PeopleAdapter(
    list: List<PeopleUIState>,
    listener: PeopleListener
) : BaseAdapter<PeopleUIState>(list, listener) {
    override val layoutID = com.elhady.movies.core.ui.R.layout.item_people
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
