package com.pau.putrautama.footballschedule.view.detail.detailMatch

import com.google.gson.Gson
import com.pau.putrautama.footballschedule.api.ApiRepository
import com.pau.putrautama.footballschedule.api.TheSportDBApi
import com.pau.putrautama.footballschedule.model.TeamResponse
import com.pau.putrautama.footballschedule.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class DetailTeamBadgePresenter(private val view: DetailMatchView,
                               private val apiRepository: ApiRepository,
                               private val gson: Gson,
                               private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamBadge(homeTeam : String?, awayTeam: String?){
        view.showDataLoading()

        async(context.main) {
            val dataHomeTeam = bg {
                gson.fromJson(
                        apiRepository.requestUrl(TheSportDBApi.getDetailTeam(homeTeam)),
                        TeamResponse::class.java
                )
            }
            val dataAwayTeam = bg {
                gson.fromJson(
                        apiRepository.requestUrl(TheSportDBApi.getDetailTeam(awayTeam)),
                        TeamResponse::class.java
                )
            }

            view.showTeambadge(dataHomeTeam.await().teamList,dataAwayTeam.await().teamList)
            view.hideDataLoading()
        }
    }
}