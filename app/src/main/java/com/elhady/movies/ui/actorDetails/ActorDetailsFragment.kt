package com.elhady.movies.ui.actorDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentActorDetailsBinding
import com.elhady.movies.domain.enums.AllMediaType
import com.elhady.movies.domain.enums.SeeAllType
import com.elhady.movies.ui.base.BaseFragment
import com.elhady.movies.utilities.collectLast
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