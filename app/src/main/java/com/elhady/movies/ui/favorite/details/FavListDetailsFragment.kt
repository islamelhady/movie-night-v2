package com.elhady.movies.ui.favorite.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elhady.movies.R

class FavListDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = FavListDetailsFragment()
    }

    private lateinit var viewModel: FavListDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fav_list_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavListDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}