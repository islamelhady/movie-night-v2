package com.elhady.movies.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentHomeBinding
import com.elhady.movies.ui.base.BaseFragment
import com.elhady.movies.ui.home.adapters.HorizontalAdapter
import com.elhady.movies.ui.home.adapters.HorizontalWrapperAdapter
import com.elhady.movies.ui.home.adapters.MovieImageAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val layoutIdFragment: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()


    val data1 = listOf("movie1", "movie2", "movie3")
    val data2 = listOf("artist1", "artist2", "artist3")
    val data3 = listOf("islam1", "islam2", "islam3")

    val horizontalAdapter = HorizontalAdapter(data1)
    val horizontalAdapter2 = HorizontalAdapter(data2)


    private val concatAdapter: ConcatAdapter by lazy {
        val config = ConcatAdapter.Config.Builder().apply {
            setIsolateViewTypes(false)
        }.build()
        ConcatAdapter(
            config,
            HorizontalWrapperAdapter(horizontalAdapter2),
            HorizontalWrapperAdapter(horizontalAdapter),
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.recyclerView.adapter = concatAdapter
    }

}