package com.elhady.movies.ui.actors

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentActorsBinding
import com.elhady.movies.ui.adapter.LoadAdapter
import com.elhady.movies.ui.base.BaseFragment
import com.elhady.movies.ui.models.ActorUiState
import com.elhady.movies.utilities.collect
import com.elhady.movies.utilities.collectLast
import com.elhady.movies.utilities.setSpanSize
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActorsFragment : BaseFragment<FragmentActorsBinding>() {
    override val layoutIdFragment: Int = R.layout.fragment_actors
    override val viewModel: ActorsViewModel by viewModels()
    private val actorsAdapter by lazy {
        ActorPagingAdapter(viewModel)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        collectEvent()
    }

    private fun setAdapter() {
        val footerAdapter = LoadAdapter(actorsAdapter::retry)
        binding.recyclerActors.adapter = actorsAdapter.withLoadStateFooter(footerAdapter)

        val mManager = binding.recyclerActors.layoutManager as GridLayoutManager
        mManager.setSpanSize(footerAdapter, actorsAdapter, mManager.spanCount)

        collect(flow = actorsAdapter.loadStateFlow, action = { viewModel.setErrorUiState(it) })

        collectLast(flow = viewModel.state.value.actors, ::setAllActors)
    }

    private suspend fun setAllActors(itemsPagingData: PagingData<ActorUiState>) {
        actorsAdapter.submitData(pagingData = itemsPagingData)
    }

    private fun collectEvent(){
        collectLast(flow = viewModel.actorsUiEvent){ event ->
            event?.getContentIfNotHandled()?.let {
                onEvent(it)
            }
        }
    }

    private fun onEvent(event: ActorsUiEvent){
        when(event){
            is ActorsUiEvent.ClickActorEvent -> {
                findNavController().navigate(ActorsFragmentDirections.actionActorsFragmentToActorDetailsFragment(event.actorID))
            }
        }
    }

}