package com.pau.putrautama.footballschedule.model

import com.google.gson.annotations.SerializedName

class SearchResponse (
        @field:SerializedName("events")
        val events: List<Event>? = null,

        @field:SerializedName("event")
        val event: List<Event>? = null,

        @field:SerializedName("teams")
        val teams: List<Team>? = null
)