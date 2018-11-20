package com.pau.putrautama.footballschedule.db

data class FavoriteMatch(
        val id: Long? = null,
        val eventId: String? = null,
        val homeTeam: String? = null,
        val awayTeam: String? = null,
        val homeScore: String? = null,
        val awayScore: String? = null,
        val eventDate: String? = null,
        val homeLineupGoalkeeper: String? = null,
        val awayLineupGoalkeeper: String? = null,
        val homeGoalDetails: String? = null,
        val awayGoalDetails: String? = null,
        val homeShots: String? = null,
        val awayShots: String? = null,
        val homeLineupDefense: String? = null,
        val awayLineupDefense: String? = null,
        val homeLineupmidfield: String? = null,
        val awayLineupMidfield: String? = null,
        val homeLineupForward: String? = null,
        val awayLineupForward: String? = null,
        val homeLineupSubstitutes: String? = null,
        val awayLineupSubstitutes: String? = null,
        val homeFormation: String? = null,
        val awayFormation: String? = null,
        val teamBadge: String? = null,
        val homeTeamId: String? = null,
        val awayTeamId: String? = null
) {

    companion object {
        const  val FAVORITE_TABLE: String = "FAVORITE_TABLE"
        const  val ID: String = "ID_"
        const  val EVENT_ID: String = "EVENT_ID"
        const  val HOME_TEAM: String = "HOME_TEAM"
        const  val AWAY_TEAM: String = "AWAY_TEAM"
        const  val HOME_SCORE: String = "HOME_SCORE"
        const  val AWAY_SCORE: String = "AWAY_SCORE"
        const  val EVENT_DATE: String = "EVENT_DATE"
        const  val HOME_LINEUP_GOALKEEPER: String = "HOME_LINEUP_GOALKEEPER"
        const  val AWAY_LINEUP_GOALKEEPER: String = "AWAY_LINEUP_GOALKEEPER"
        const  val HOME_GOAL_DETAILS: String = "HOME_GOAL_DETAILS"
        const  val AWAY_GOAL_DETAILS: String = "AWAY_GOAL_DETAILS"
        const  val HOME_SHOTS: String = "HOME_SHOTS"
        const  val AWAY_SHOTS: String = "AWAY_SHOTS"
        const  val HOME_LINEUP_DEFENSE: String = "HOME_LINEUP_DEFENSE"
        const  val AWAY_LINEUP_DEFENSE: String = "AWAY_LINEUP_DEFENSE"
        const  val HOME_LINEUP_MIDFIELD: String = "HOME_LINEUP_MIDFIELD"
        const  val AWAY_LINEUP_MIDFIELD: String = "AWAY_LINEUP_MIDFIELD"
        const  val HOME_LINEUP_FORWARD: String = "HOME_LINEUP_FORWARD"
        const  val AWAY_LINEUP_FORWARD: String = "AWAY_LINEUP_FORWARD"
        const  val HOME_LINEUP_SUBSTITUTES: String = "HOME_LINEUP_SUBSTITUTES"
        const  val AWAY_LINEUP_SUBSTITUTES: String = "AWAY_LINEUP_SUBSTITUTES"
        const  val HOME_FORMATION: String = "HOME_FORMATION"
        const  val AWAY_FORMATION: String = "AWAY_FORMATION"
        const  val STR_TEAM_BADGE: String = "TEAM_BADGE"
        const  val HOME_TEAM_ID: String = "HOME_TEAM_ID"
        const  val AWAY_TEAM_ID: String = "AWAY_TEAM_ID"
    }
}