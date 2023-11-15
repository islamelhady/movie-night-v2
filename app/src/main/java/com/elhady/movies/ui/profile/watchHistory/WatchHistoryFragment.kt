package com.elhady.movies.ui.profile.watchHistory

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elhady.movies.R

class WatchHistoryFragment : Fragment() {

    companion object {
        fun newInstance() = WatchHistoryFragment()
    }

    private lateinit var viewModel: WatchHistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_watch_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WatchHistoryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}