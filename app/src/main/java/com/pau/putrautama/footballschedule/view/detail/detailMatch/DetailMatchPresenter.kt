package com.pau.putrautama.footballschedule.view.detail.detailMatch

import com.google.gson.Gson
import com.pau.putrautama.footballschedule.api.ApiRepository
import com.pau.putrautama.footballschedule.api.TheSportDBApi
import com.pau.putrautama.footballschedule.model.EventResponse
import com.pau.putrautama.footballschedule.util.CoroutineContextProvider
import com.pau.putrautama.footballschedule.view.fragments.match.MatchView
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class DetailMatchPresenter(private val view : MatchView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getDetailEvent(eventId : String?){
        view.showDataLoading()

        async(context.main) {
            val data = bg{
                gson.fromJson(
                        apiRepository.requestUrl(TheSportDBApi.getDetailMatch(eventId)),
                        EventResponse::class.java
                )
        }
            view.showEventList(data.await().eventResponse)
            view.hideDataLoading()
        }
    }


}