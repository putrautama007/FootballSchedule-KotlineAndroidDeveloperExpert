package com.pau.putrautama.footballschedule.view.fragments.match.prevMatch

import com.google.gson.Gson
import com.pau.putrautama.footballschedule.api.ApiRepository
import com.pau.putrautama.footballschedule.api.TheSportDBApi
import com.pau.putrautama.footballschedule.model.EventResponse
import com.pau.putrautama.footballschedule.model.SearchResponse
import com.pau.putrautama.footballschedule.view.fragments.match.MatchView
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PrevMatchPresenter (private val view: MatchView,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson){

    fun getPrevEventList(event : String?, search : String?) {
        view.showDataLoading()

        async(UI) {
            if (search == "empty_input") {
            val data = bg{
                gson.fromJson(
                        apiRepository.requestUrl(TheSportDBApi.getPrevMatch(event)),
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