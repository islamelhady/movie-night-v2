package com.elhady.ui.profile.logout

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.elhady.base.BaseDialog
import com.elhady.ui.R
import com.elhady.ui.databinding.LogoutDialogBinding
import com.elhady.viewmodel.profile.logout.LogoutUiEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogoutDialog : BaseDialog<LogoutDialogBinding>() {

    override val layoutIdFragment: Int = R.layout.logout_dialog
    override val viewModel: LogoutDialogViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setWidthPercent(90)
        collectLast(viewModel.logoutUIEvent) { event ->
            event?.getContentIfNotHandled()?.let { onEvent(it) }
        }
    }

    private fun onEvent(event: LogoutUiEvent) {
        when (event) {
            LogoutUiEvent.CloseDialogEvent -> {
                dismiss()
            }
            LogoutUiEvent.LogoutEvent -> {
                findNavController().navigate(LogoutDialogDirections.actionLogoutDialogToHomeFragment())
            }
        }
    }

}