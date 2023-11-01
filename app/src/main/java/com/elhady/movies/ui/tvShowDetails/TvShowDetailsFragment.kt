package com.elhady.movies.ui.tvShowDetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elhady.movies.R

class TvShowDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = TvShowDetailsFragment()
    }

    private lateinit var viewModel: TvShowDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_show_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TvShowDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}