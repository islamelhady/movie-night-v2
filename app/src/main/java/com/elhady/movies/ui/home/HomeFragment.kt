package com.elhady.movies.ui.home

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.elhady.movies.R
import com.elhady.movies.data.database.MovieDatabase
import com.elhady.movies.data.database.entity.Saved
import com.elhady.movies.data.database.entity.SearchHistory
import com.elhady.movies.databinding.FragmentHomeBinding
import com.elhady.movies.ui.base.BaseFragment
import com.elhady.movies.ui.home.adapters.HorizontalAdapter
import com.elhady.movies.ui.home.adapters.HorizontalWrapperAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val layoutIdFragment: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()


    val data1 = listOf("movie1", "movie2", "movie3","movie4", "movie5", "movie6","movie7", "movie8", "movie9")
    val data2 = listOf("artist1", "artist2", "artist3","artist4", "artist5", "artist6","artist7", "artist8", "artist9")
    val data3 = listOf("islam1", "islam2", "islam3","islam4", "islam5", "islam6","islam7", "islam8", "islam9")


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