package com.pau.putrautama.footballschedule.view.fragments.match

import com.pau.putrautama.footballschedule.model.Event

interface MatchView {
    fun showDataLoading()
    fun hideDataLoading()
    fun showEventList(event : List<Event>?)
}