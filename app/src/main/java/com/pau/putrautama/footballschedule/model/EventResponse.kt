package com.pau.putrautama.footballschedule.model

import com.google.gson.annotations.SerializedName

data class EventResponse (
        @field:SerializedName("events")
        val eventResponse: List<Event>
)