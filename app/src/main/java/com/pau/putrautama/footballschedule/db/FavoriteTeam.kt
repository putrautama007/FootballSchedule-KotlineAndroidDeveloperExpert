package com.pau.putrautama.footballschedule.db

data class FavoriteTeam(
        val id: Long? = null,
        val teamId: String? = null,
        val teamName: String? = null,
        val teamBadge: String? = null,
        val teamYear: String? = null,
        val teamStadium: String? = null) {

    companion object {
        const val FAVORITE_TEAM_TABLE: String = "FAVORITE_TEAM_TABLE"
        const val FAVORITE_TEAM_ID: String = "FAVORITE_TEAM_ID"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val TEAM_BADGE: String = "TEAM_BADGE"
        const val TEAM_YEAR: String = "TEAM_YEAR"
        const val TEAM_STADIUM = "TEAM_STADIUM"
    }
}