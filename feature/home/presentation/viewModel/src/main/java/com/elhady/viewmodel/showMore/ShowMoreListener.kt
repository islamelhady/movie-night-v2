package com.elhady.viewmodel.showMore

import com.elhady.base.BaseInteractionListener


interface ShowMoreListener : BaseInteractionListener {
    fun onClickMedia(id: Int)
    fun backNavigate()
}
