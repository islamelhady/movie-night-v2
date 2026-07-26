package com.elhady.movies.feature.details.presentation.ui.peopledetails

import com.elhady.movies.feature.details.BR
import com.elhady.movies.feature.details.R
import com.elhady.movies.core.ui.bases.BaseAdapter
import com.elhady.movies.feature.details.presentation.peopledetails.PeopleDetailsListener
import com.elhady.movies.feature.details.presentation.peopledetails.PersonDetailsUiState


class  PeopleDetailsRecyclerAdapter(items: List<PersonDetailsUiState.PeopleMediaUiState>, listener: PeopleDetailsListener):
    BaseAdapter<PersonDetailsUiState.PeopleMediaUiState>(items, listener) {

    override val layoutID = R.layout.item_people_media
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
