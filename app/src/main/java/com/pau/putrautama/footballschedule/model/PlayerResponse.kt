package com.pau.putrautama.footballschedule.model

import com.google.gson.annotations.SerializedName

class PlayerResponse (
        @SerializedName("player")
        var playerResponse: List<Player>)