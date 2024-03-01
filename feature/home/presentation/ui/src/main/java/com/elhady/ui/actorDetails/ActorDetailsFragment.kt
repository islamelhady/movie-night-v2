package com.elhady.ui.actorDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.elhady.base.BaseFragment
import com.elhady.ui.R
import com.elhady.ui.databinding.FragmentActorDetailsBinding
import com.elhady.viewmodel.actorDetails.ActorDetailsUiEvent
import com.elhady.viewmodel.actorDetails.ActorDetailsUiState
import com.elhady.viewmodel.actorDetails.ActorDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActorDetailsFragment : BaseFragment<FragmentActorDetailsBinding, ActorDetailsUiState, ActorDetailsUiEvent>() {
    override val layoutIdFragment: Int = R.layout.fragment_actor_details
    override val viewModel: ActorDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(false)
        binding.recyclerRelatedMovie.adapter = ActorMoviesAdapter(mutableListOf(), viewModel)
    }

    override fun onEvent(event: ActorDetailsUiEvent) {
        when (event) {
            is ActorDetailsUiEvent.ClickMovieEvent -> {
                findNavController().navigate(
                    ActorDetailsFragmentDirections.actionActorDetailsFragmentToMovieDetailsFragment(
                        event.movieID
                    )
                )
            }
            ActorDetailsUiEvent.ClickSeeAllEvent -> {
                findNavController().navigate(ActorDetailsFragmentDirections.actionActorDetailsFragmentToAllMediaFragment(SeeAllType.ACTOR_MOVIES, viewModel.args.actorID))

            }
            is ActorDetailsUiEvent.ClickBackButton -> {
                findNavController().popBackStack()
            }
        }

    }


}