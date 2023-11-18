package com.elhady.movies.ui.favorite

import com.elhady.movies.domain.usecases.favList.CreateListUseCase
import com.elhady.movies.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject  constructor(
    private val createListUseCase: CreateListUseCase
) : BaseViewModel() {


    override fun getData() {
    }
}