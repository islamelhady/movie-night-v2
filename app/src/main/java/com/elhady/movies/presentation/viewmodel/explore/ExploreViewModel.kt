package com.elhady.movies.presentation.viewmodel.explore

import com.elhady.movies.core.bases.BaseViewModel

class ExploreViewModel : BaseViewModel<ExploreUiState, ExploreUiEvent>(ExploreUiState()), ExploreListener{
    override fun onClickSearch() {
       sendEvent(ExploreUiEvent.NavigateToSearchEvent)
    }

}