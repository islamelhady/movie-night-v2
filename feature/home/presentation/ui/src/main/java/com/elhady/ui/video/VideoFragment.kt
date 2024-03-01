package com.elhady.ui.video

import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.viewModels
import com.elhady.base.BaseFragment
import com.elhady.ui.R
import com.elhady.ui.databinding.FragmentVideoBinding
import com.elhady.viewmodel.video.TrailerUiState
import com.elhady.viewmodel.video.VideoInteracion
import com.elhady.viewmodel.video.VideoViewModel
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

