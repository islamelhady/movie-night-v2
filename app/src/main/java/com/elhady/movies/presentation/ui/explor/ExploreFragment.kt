package com.elhady.movies.presentation.ui.explor

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseFragment
import com.elhady.movies.databinding.FragmentExploreBinding
import com.elhady.movies.presentation.viewmodel.explore.ExploreUiEvent
import com.elhady.movies.presentation.viewmodel.explore.ExploreUiState
import com.elhady.movies.presentation.viewmodel.explore.ExploreViewModel

class ExploreFragment : BaseFragment<FragmentExploreBinding, ExploreUiState, ExploreUiEvent>() {

    override val layoutIdFragment: Int = R.layout.fragment_explore
    override val viewModel: ExploreViewModel  by viewModels()


    override fun onEvent(event: ExploreUiEvent) {
        when(event){
            ExploreUiEvent.NavigateToSearchEvent -> navigateToSearch()
        }
    }


    private fun navigateToSearch() {
        val extras = FragmentNavigatorExtras(binding.inputSearch to "search_box")
        Navigation.findNavController(binding.root)
            .navigate(
                ExploreFragmentDirections.actionExploreFragmentToSearchFragment(),
                extras
            )
    }

}