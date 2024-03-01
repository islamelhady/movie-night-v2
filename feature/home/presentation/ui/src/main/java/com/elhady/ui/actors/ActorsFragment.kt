package com.elhady.ui.actors

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.elhady.base.BaseFragment
import com.elhady.ui.adapter.LoadAdapter
import com.elhady.ui.R
import com.elhady.ui.databinding.FragmentActorsBinding
import com.elhady.viewmodel.actors.ActorsUiEvent
import com.elhady.viewmodel.actors.ActorsUiState
import com.elhady.viewmodel.actors.ActorsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.EmptyFlow.collect

@AndroidEntryPoint
class ActorsFragment : BaseFragment<FragmentActorsBinding, ActorsUiState, ActorsUiEvent>() {
    override val layoutIdFragment: Int = R.layout.fragment_actors
    override val viewModel: ActorsViewModel by viewModels()
    private val actorsAdapter by lazy {
        ActorPagingAdapter(viewModel)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(true)
        setAdapter()
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

    override fun onEvent(event: ActorsUiEvent){
        when(event){
            is ActorsUiEvent.ClickActorEvent -> {
                findNavController().navigate(ActorsFragmentDirections.actionActorsFragmentToActorDetailsFragment(event.actorID))
            }
        }
    }

}