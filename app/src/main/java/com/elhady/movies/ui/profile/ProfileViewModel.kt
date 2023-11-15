package com.elhady.movies.ui.profile

import com.elhady.movies.domain.usecases.CheckIfLoggedInUseCase
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.ui.models.MediaUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val checkIfLoggedInUseCase: CheckIfLoggedInUseCase)  : BaseViewModel() {

    private val _profileUiState = MediaUiState()

    init {
        getData()
    }
    override fun getData() {
        TODO("Not yet implemented")
    }
}