package com.pau.putrautama.footballschedule.view.fragments.team.player

import com.google.gson.Gson
import com.pau.putrautama.footballschedule.api.ApiRepository
import com.pau.putrautama.footballschedule.api.TheSportDBApi
import com.pau.putrautama.footballschedule.model.PlayerResponse
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PlayerPresenter(private val view: PlayerView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson) {

    fun getPlayerList(teamId : String){
        view.showDataLoading()

        async(UI) {
            val player = bg {
                gson.fromJson(apiRepository
                        .requestUrl(TheSportDBApi.getPlayer(teamId)),
                        PlayerResponse::class.java)
            }

            view.showPlayerList(player.await().playerResponse)
            view.hideDataLoading()
        }
    }
}