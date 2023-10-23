package com.elhady.movies.ui.actorDetails

import androidx.fragment.app.viewModels
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentActorDetailsBinding
import com.elhady.movies.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActorDetailsFragment : BaseFragment<FragmentActorDetailsBinding>() {
    override val layoutIdFragment: Int = R.layout.fragment_actor_details
    override val viewModel: ActorDetailsViewModel by viewModels()


}