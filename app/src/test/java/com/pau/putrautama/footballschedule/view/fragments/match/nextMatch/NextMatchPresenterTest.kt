package com.pau.putrautama.footballschedule.view.fragments.match.nextMatch

import com.google.gson.Gson
import com.pau.putrautama.footballschedule.api.ApiRepository
import com.pau.putrautama.footballschedule.api.TheSportDBApi
import com.pau.putrautama.footballschedule.model.Event
import com.pau.putrautama.footballschedule.model.EventResponse
import com.pau.putrautama.footballschedule.util.TestContextProvider
import com.pau.putrautama.footballschedule.view.fragments.match.MatchView
import org.junit.Test
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class NextMatchPresenterTest {

    @Mock
    private lateinit var view: MatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: NextMatchPresenter
    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = NextMatchPresenter(view,apiRepository,gson,TestContextProvider())
    }

    @Test
    fun getNextEventList() {
        val event: MutableList<Event> = mutableListOf()
        val response = EventResponse(event)
        val matchId = "1234"
        val search ="Arsenal"

        `when`(gson.fromJson(apiRepository
                .requestUrl(TheSportDBApi.getNextMatch(matchId)),
                EventResponse::class.java)
        ).thenReturn(response)

        presenter.getNextEventList(matchId,search)

        verify(view).showDataLoading()
        verify(view).showEventList(event)
        verify(view).hideDataLoading()

    }
}