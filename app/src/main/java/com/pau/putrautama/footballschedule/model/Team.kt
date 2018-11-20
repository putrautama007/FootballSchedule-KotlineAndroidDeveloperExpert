package com.pau.putrautama.footballschedule.model

import com.google.gson.annotations.SerializedName

data class Team (
        @SerializedName( "strTeamBadge")
        val teamBadge : String? = null,

        @SerializedName("idTeam")
        var teamId: String? = null,

        @SerializedName("strTeam")
        var teamName: String? = null,

        @SerializedName("intFormedYear")
        var teamFormedYear: String? = null,

        @SerializedName("strStadium")
        var teamStadium: String? = null,

        @SerializedName("strDescriptionEN")
        var teamDescription: String? = null
)