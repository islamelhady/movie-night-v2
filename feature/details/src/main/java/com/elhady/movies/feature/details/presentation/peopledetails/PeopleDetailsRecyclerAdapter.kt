package com.elhady.movies.feature.details.presentation.peopledetails

import com.elhady.movies.feature.details.BR
import com.elhady.movies.feature.details.R
import com.elhady.movies.core.ui.base.BaseAdapter

class PeopleDetailsRecyclerAdapter(items: List<PeopleDetailsUiState.PeopleMediaUiState>, listener: PeopleDetailsListener):
    BaseAdapter<PeopleDetailsUiState.PeopleMediaUiState>(items, listener) {

    override val layoutID = R.layout.item_people_media
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
