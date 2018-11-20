package com.pau.putrautama.footballschedule.view.detail.detailMatch


import com.pau.putrautama.footballschedule.model.Team

interface DetailMatchView {
    fun showDataLoading()
    fun hideDataLoading()
    fun showTeambadge(homeTeam : List<Team>?, awayTeam : List<Team>?)

}