package com.pau.putrautama.footballschedule.view.fragments.team

import com.google.gson.Gson
import com.pau.putrautama.footballschedule.api.ApiRepository
import com.pau.putrautama.footballschedule.api.TheSportDBApi
import com.pau.putrautama.footballschedule.model.SearchResponse
import com.pau.putrautama.footballschedule.model.TeamResponse
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamPresenter(private val view: TeamView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson) {

    fun getTeamList(team : String?, search : String?){
        view.showDataLoading()
        async(UI) {
            if (search == "empty_input") {
                val teams = bg {
                    gson.fromJson(apiRepository
                            .requestUrl(TheSportDBApi.getTeam(team)),
                            TeamResponse::class.java)
                }
                view.showTeamList(teams.await().teamList)
                view.hideDataLoading()
            }else if(team == "empty_input"){
                val data = bg {
                    gson.fromJson(
                            apiRepository.requestUrl(TheSportDBApi.getSearchTeam(search)),
                            SearchResponse::class.java)
                }
                view.showTeamList(data.await().teams)
                view.hideDataLoading()
            }
        }
    }
}