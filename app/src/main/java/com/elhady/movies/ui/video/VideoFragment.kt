package com.elhady.movies.ui.video

import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.viewModels
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentVideoBinding
import com.elhady.movies.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoFragment : BaseFragment<FragmentVideoBinding, TrailerUiState, VideoInteracion>() {
    override val layoutIdFragment: Int = R.layout.fragment_video
    override val viewModel: VideoViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fullScreen(requireActivity().window)
    }

    override fun onEvent(event: VideoInteracion) {
        TODO("Not yet implemented")
    }

    private fun fullScreen(window: Window) {
        WindowInsetsControllerCompat(window, binding.youtubePlayer).apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

}

