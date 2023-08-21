package com.elhady.movies.ui.profile

import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentProfileBinding
import com.elhady.movies.ui.base.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    override val layoutIdFragment: Int = R.layout.fragment_profile
    override val viewModelClass = ProfileViewModel::class.java


}