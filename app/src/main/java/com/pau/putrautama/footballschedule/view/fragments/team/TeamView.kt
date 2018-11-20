package com.pau.putrautama.footballschedule.view.fragments.team

import com.pau.putrautama.footballschedule.model.Team

interface TeamView {
    fun showDataLoading()
    fun hideDataLoading()
    fun showTeamList(team : List<Team>?)
}