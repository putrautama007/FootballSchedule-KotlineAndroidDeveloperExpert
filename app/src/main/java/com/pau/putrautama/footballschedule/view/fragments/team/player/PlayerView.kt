package com.pau.putrautama.footballschedule.view.fragments.team.player

import com.pau.putrautama.footballschedule.model.Player

interface PlayerView {
    fun showDataLoading()
    fun hideDataLoading()
    fun showPlayerList(team : List<Player>)
}