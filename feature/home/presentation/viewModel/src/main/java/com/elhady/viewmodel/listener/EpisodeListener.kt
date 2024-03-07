package com.elhady.viewmodel.listener

import com.elhady.base.BaseInteractionListener


interface EpisodeListener: BaseInteractionListener {
    fun onClickEpisode(id: Int)
}