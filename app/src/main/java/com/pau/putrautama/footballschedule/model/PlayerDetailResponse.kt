package com.pau.putrautama.footballschedule.model

import com.google.gson.annotations.SerializedName

class PlayerDetailResponse (
    @field:SerializedName("players")
    val playerDetailRespon: List<Player>)
