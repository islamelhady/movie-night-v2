package com.elhady.movies.ui.profile.watchHistory

import androidx.fragment.app.viewModels
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentWatchHistoryBinding
import com.elhady.movies.ui.base.BaseFragment

class WatchHistoryFragment : BaseFragment<FragmentWatchHistoryBinding>() {

    override val layoutIdFragment: Int = R.layout.fragment_watch_history
    override val viewModel: WatchHistoryViewModel by viewModels()


}