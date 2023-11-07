package com.elhady.movies.ui.video

import androidx.fragment.app.viewModels
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentVideoBinding
import com.elhady.movies.ui.base.BaseFragment

class VideoFragment : BaseFragment<FragmentVideoBinding>() {
    override val layoutIdFragment: Int = R.layout.fragment_video
    override val viewModel: VideoViewModel by viewModels()


}