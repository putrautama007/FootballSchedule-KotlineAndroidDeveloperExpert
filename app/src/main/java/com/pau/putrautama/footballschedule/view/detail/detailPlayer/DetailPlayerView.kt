package com.pau.putrautama.footballschedule.view.detail.detailPlayer

import com.pau.putrautama.footballschedule.model.Player

interface DetailPlayerView {
    fun showDataLoading()
    fun hideDataLoading()
    fun showDetailPlayer(player : List<Player>)
}
