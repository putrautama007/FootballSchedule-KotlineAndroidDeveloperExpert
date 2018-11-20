package com.pau.putrautama.footballschedule.model

import com.google.gson.annotations.SerializedName

data class TeamResponse(
        @SerializedName("teams")
        val teamList: List<Team>
)