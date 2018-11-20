package com.pau.putrautama.footballschedule.view.detail.detailTeam

import com.pau.putrautama.footballschedule.model.Team

interface DetailTeamView {
    fun showDataLoading()
    fun hideDataLoading()
    fun showDetailTeam(team : List<Team>)
}