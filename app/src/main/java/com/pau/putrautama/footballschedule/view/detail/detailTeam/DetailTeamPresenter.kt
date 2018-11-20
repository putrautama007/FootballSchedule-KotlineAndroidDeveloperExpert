package com.pau.putrautama.footballschedule.view.detail.detailTeam

import com.google.gson.Gson
import com.pau.putrautama.footballschedule.api.ApiRepository
import com.pau.putrautama.footballschedule.api.TheSportDBApi
import com.pau.putrautama.footballschedule.model.TeamResponse
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class DetailTeamPresenter(private val view: DetailTeamView,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson) {

    fun getDetailTeam(teamId : String){
        view.showDataLoading()

        async(UI) {
            val team = bg {
                gson.fromJson(apiRepository
                        .requestUrl(TheSportDBApi.getDetailTeam(teamId)),
                        TeamResponse::class.java)
            }

            view.showDetailTeam(team.await().teamList)
            view.hideDataLoading()
        }
    }
}