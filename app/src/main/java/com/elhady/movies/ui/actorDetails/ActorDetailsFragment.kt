package com.elhady.movies.ui.actorDetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elhady.movies.R

class ActorDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = ActorDetailsFragment()
    }

    private lateinit var viewModel: ActorDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_actor_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ActorDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}