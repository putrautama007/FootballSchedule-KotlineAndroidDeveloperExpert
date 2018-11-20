package com.pau.putrautama.footballschedule.model

import com.google.gson.annotations.SerializedName

data class Player( @SerializedName("dateBorn")
                   var dateBorn: String? = null,
                   @SerializedName("dateSigned")
                   var dateSigned: String? = null,
                   @SerializedName("idPlayer")
                   var idPlayer: String? = null,
                   @SerializedName("idPlayerManager")
                   var idPlayerManager: String? = null,
                   @SerializedName("idSoccerXML")
                   var idSoccerXML: String? = null,
                   @SerializedName("idTeam")
                   var idTeam: String? = null,
                   @SerializedName("intLoved")
                   var loved: String? = null,
                   @SerializedName("intSoccerXMLTeamID")
                   var soccerXMLTeamID: String? = null,
                   @SerializedName("strBanner")
                   var banner: String? = null,
                   @SerializedName("strBirthLocation")
                   var birthLocation: String? = null,
                   @SerializedName("strCollege")
                   var college: String? = null,
                   @SerializedName("strCutout")
                   var cutout: String? = null,
                   @SerializedName("strDescriptionEN")
                   var descriptionEN: String? = null,
                   @SerializedName("strFanart1")
                   var fanart1: String? = null,
                   @SerializedName("strFanart2")
                   var fanart2: String? = null,
                   @SerializedName("strFanart3")
                   var fanart3: String? = null,
                   @SerializedName("strFanart4")
                   var fanart4: String? = null,
                   @SerializedName("strGender")
                   var gender: String? = null,
                   @SerializedName("strHeight")
                   var height: String? = null,
                   @SerializedName("strInstagram")
                   var instagram: String? = null,
                   @SerializedName("strLocked")
                   var locked: String? = null,
                   @SerializedName("strNationality")
                   var nationality: String? = null,
                   @SerializedName("strPlayer")
                   var player: String? = null,
                   @SerializedName("strPosition")
                   var position: String? = null,
                   @SerializedName("strSigning")
                   var signing: String? = null,
                   @SerializedName("strSport")
                   var sport: String? = null,
                   @SerializedName("strTeam")
                   var team: String? = null,
                   @SerializedName("strThumb")
                   var thumb: String? = null,
                   @SerializedName("strTwitter")
                   var twitter: String? = null,
                   @SerializedName("strWage")
                   var wage: String? = null,
                   @SerializedName("strWebsite")
                   var website: String? = null,
                   @SerializedName("strWeight")
                   var weight: String? = null,
                   @SerializedName("strYoutube")
                   var youtube: String? = null)