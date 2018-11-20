package com.pau.putrautama.footballschedule.view.fragments.match.nextMatch

import com.google.gson.Gson
import com.pau.putrautama.footballschedule.api.ApiRepository
import com.pau.putrautama.footballschedule.api.TheSportDBApi
import com.pau.putrautama.footballschedule.model.EventResponse
import com.pau.putrautama.footballschedule.model.SearchResponse
import com.pau.putrautama.footballschedule.util.CoroutineContextProvider
import com.pau.putrautama.footballschedule.view.fragments.match.MatchView
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class NextMatchPresenter (private val view: MatchView,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson,
                          private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getNextEventList(event : String?, search : String?) {
        view.showDataLoading()

        async(context.main) {
            if (search == "empty_input") {
                val data = bg {
                    gson.fromJson(
                            apiRepository.requestUrl(TheSportDBApi.getNextMatch(event)),
                            EventResponse::class.java)
                }
                view.showEventList(data.await().eventResponse)
                view.hideDataLoading()
            }else if(event == "empty_input"){
                val data = bg {
                    gson.fromJson(
                            apiRepository.requestUrl(TheSportDBApi.getSearchEvent(search)),
                            SearchResponse::class.java)
                }
                view.showEventList(data.await().event)
                view.hideDataLoading()
            }
        }
    }
}