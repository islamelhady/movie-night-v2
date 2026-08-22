package com.elhady.movies.feature.search.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.elhady.movies.feature.search.BR
import com.elhady.movies.feature.search.R
import com.elhady.movies.feature.search.databinding.BottomSheetSearchFilterBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterMovieBottomSheetFragment : BottomSheetDialogFragment(), SearchListener {
    private lateinit var binding: BottomSheetSearchFilterBinding
    val viewModel by activityViewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_search_filter, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.viewModel, viewModel)
            setVariable(BR.listener, this@FilterMovieBottomSheetFragment)
            return root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.listener = this
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun onClickFilter() {}

    override fun onClickGenre(genresId: Int) {
        viewModel.onEvent(SearchUiEvent.GenreClicked(genresId))
    }

    override fun onClickClear() {}

    override fun showResultMovie() {}

    override fun showResultTv() {}

    override fun showResultPeople() {}

    override fun onClickBack() {}

    override fun onClickMedia(id: Int) {}

    override fun onClickPeople(id: Int) {}

    override fun onClickTryAgain() {}

    override fun onClickApply() {
        viewModel.onEvent(SearchUiEvent.ApplyFilterClicked)
        dismiss()
    }
}
