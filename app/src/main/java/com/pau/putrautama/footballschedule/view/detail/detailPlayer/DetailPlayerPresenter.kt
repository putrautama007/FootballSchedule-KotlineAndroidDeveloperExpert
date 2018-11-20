package com.pau.putrautama.footballschedule.view.detail.detailPlayer

import com.google.gson.Gson
import com.pau.putrautama.footballschedule.api.ApiRepository
import com.pau.putrautama.footballschedule.api.TheSportDBApi
import com.pau.putrautama.footballschedule.model.PlayerDetailResponse
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class DetailPlayerPresenter(private val view: DetailPlayerView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson) {

    fun getDetailPlayer(playerId : String){
        view.showDataLoading()

        async(UI) {
            val player = bg {
                gson.fromJson(apiRepository
                        .requestUrl(TheSportDBApi.getDetailPlayer(playerId)),
                        PlayerDetailResponse::class.java)
            }

            view.showDetailPlayer(player.await().playerDetailRespon)
            view.hideDataLoading()
        }
    }
}