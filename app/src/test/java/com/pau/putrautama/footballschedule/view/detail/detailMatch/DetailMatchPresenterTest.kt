package com.pau.putrautama.footballschedule.view.detail.detailMatch

import com.google.gson.Gson
import com.pau.putrautama.footballschedule.api.ApiRepository
import com.pau.putrautama.footballschedule.api.TheSportDBApi
import com.pau.putrautama.footballschedule.model.Event
import com.pau.putrautama.footballschedule.model.EventResponse
import com.pau.putrautama.footballschedule.util.TestContextProvider
import com.pau.putrautama.footballschedule.view.fragments.match.MatchView
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class DetailMatchPresenterTest{
    @Mock
    private lateinit var view: MatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: DetailMatchPresenter
    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = DetailMatchPresenter(view,apiRepository,gson, TestContextProvider())
    }
    @Test
    fun getDetailEventList() {
        val event: MutableList<Event> = mutableListOf()
        val response = EventResponse(event)
        val matchId = "1234"

        `when`(gson.fromJson(
                apiRepository.requestUrl(TheSportDBApi.getDetailMatch(matchId)),
                EventResponse::class.java)).thenReturn(response)
        presenter.getDetailEvent(matchId)

        Mockito.verify(view).showDataLoading()
        Mockito.verify(view).showEventList(event)
        Mockito.verify(view).hideDataLoading()

    }
}