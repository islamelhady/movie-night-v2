package com.elhady.movies.ui.profile.logout

import androidx.fragment.app.viewModels
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentLogoutDialogBinding
import com.elhady.movies.ui.base.BaseDialog

class LogoutDialog : BaseDialog<FragmentLogoutDialogBinding>() {

    override val layoutIdFragment: Int = R.layout.fragment_logout_dialog
    override val viewModel: LogoutDialogViewModel by viewModels()


}