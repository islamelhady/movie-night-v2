package com.elhady.movies.ui.actorDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentActorDetailsBinding
import com.elhady.movies.domain.enums.AllMediaType
import com.elhady.movies.ui.base.BaseFragment
import com.elhady.movies.utilities.collectLast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActorDetailsFragment : BaseFragment<FragmentActorDetailsBinding>() {
    override val layoutIdFragment: Int = R.layout.fragment_actor_details
    override val viewModel: ActorDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerRelatedMovie.adapter = ActorMoviesAdapter(mutableListOf(), viewModel)
        collectEvent()

    }

    private fun collectEvent() {
        collectLast(viewModel.uiEvent) { event ->
            event?.getContentIfNotHandled()?.let {
                onEvent(it)
            }
        }
    }

    private fun onEvent(event: ActorDetailsUiEvent) {
        when (event) {
            is ActorDetailsUiEvent.ClickMovieEvent -> {
                findNavController().navigate(
                    ActorDetailsFragmentDirections.actionActorDetailsFragmentToMovieDetailsFragment(
                        event.movieID
                    )
                )
            }

            is ActorDetailsUiEvent.ClickSeeAllEvent -> {
                findNavController().navigate(ActorDetailsFragmentDirections.actionActorDetailsFragmentToMoviesFragment(AllMediaType.ACTOR_MOVIES, viewModel.args.actorID))

            }
            is ActorDetailsUiEvent.ClickBackButton -> {
                findNavController().popBackStack()
            }
        }

    }


}