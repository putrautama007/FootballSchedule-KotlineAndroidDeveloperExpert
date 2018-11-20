package com.pau.putrautama.footballschedule.view.detail.detailMatch

import com.google.gson.Gson
import com.pau.putrautama.footballschedule.api.ApiRepository
import com.pau.putrautama.footballschedule.api.TheSportDBApi
import com.pau.putrautama.footballschedule.model.Team
import com.pau.putrautama.footballschedule.model.TeamResponse
import com.pau.putrautama.footballschedule.util.TestContextProvider
import org.junit.Test
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class DetailTeamBadgePresenterTest {

    @Mock
    private lateinit var view: DetailMatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: DetailTeamBadgePresenter
    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = DetailTeamBadgePresenter(view,apiRepository,gson, TestContextProvider())
    }

    @Test
    fun getTeamBadge() {
        val data: MutableList<Team> = mutableListOf()
        val response = TeamResponse(data)
        val id = "1234"

      `when`(gson.fromJson(apiRepository
                .requestUrl(TheSportDBApi.getDetailTeam(id)),
                TeamResponse::class.java)
        ).thenReturn(response)

        presenter.getTeamBadge(id, id)

        verify(view).showDataLoading()
        verify(view).showTeambadge(response.teamList, response.teamList)
        verify(view).hideDataLoading()
    }
}