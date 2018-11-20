package com.pau.putrautama.footballschedule.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.AWAY_FORMATION
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.AWAY_GOAL_DETAILS
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.AWAY_LINEUP_DEFENSE
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.AWAY_LINEUP_FORWARD
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.AWAY_LINEUP_GOALKEEPER
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.AWAY_LINEUP_MIDFIELD
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.AWAY_LINEUP_SUBSTITUTES
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.AWAY_SCORE
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.AWAY_SHOTS
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.AWAY_TEAM
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.AWAY_TEAM_ID
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.EVENT_DATE
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.EVENT_ID
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.FAVORITE_TABLE
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.HOME_FORMATION
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.HOME_GOAL_DETAILS
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.HOME_LINEUP_DEFENSE
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.HOME_LINEUP_FORWARD
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.HOME_LINEUP_GOALKEEPER
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.HOME_LINEUP_MIDFIELD
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.HOME_LINEUP_SUBSTITUTES
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.HOME_SCORE
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.HOME_SHOTS
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.HOME_TEAM
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.HOME_TEAM_ID
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.ID
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.STR_TEAM_BADGE
import com.pau.putrautama.footballschedule.db.FavoriteTeam.Companion.FAVORITE_TEAM_ID
import com.pau.putrautama.footballschedule.db.FavoriteTeam.Companion.FAVORITE_TEAM_TABLE
import com.pau.putrautama.footballschedule.db.FavoriteTeam.Companion.TEAM_BADGE
import com.pau.putrautama.footballschedule.db.FavoriteTeam.Companion.TEAM_ID
import com.pau.putrautama.footballschedule.db.FavoriteTeam.Companion.TEAM_NAME
import com.pau.putrautama.footballschedule.db.FavoriteTeam.Companion.TEAM_STADIUM
import com.pau.putrautama.footballschedule.db.FavoriteTeam.Companion.TEAM_YEAR

import org.jetbrains.anko.db.*

class DatabaseOpenHelper(context : Context)
    : ManagedSQLiteOpenHelper(context,
        "favorite.db", null, 1) {

    companion object {
        private var instance: DatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx : Context) : DatabaseOpenHelper{
           if (instance == null){
               instance = DatabaseOpenHelper(ctx.applicationContext)
           }
            return instance as DatabaseOpenHelper
        }
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(FAVORITE_TABLE, true,
                ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                EVENT_ID to TEXT + UNIQUE,
                HOME_TEAM to TEXT,
                AWAY_TEAM to TEXT,
                HOME_SCORE to TEXT,
                AWAY_SCORE to TEXT,
                EVENT_DATE to TEXT,
                HOME_LINEUP_GOALKEEPER to TEXT,
                AWAY_LINEUP_GOALKEEPER to TEXT,
                HOME_GOAL_DETAILS to TEXT,
                AWAY_GOAL_DETAILS to TEXT,
                HOME_SHOTS to TEXT,
                AWAY_SHOTS to TEXT,
                HOME_LINEUP_DEFENSE to TEXT,
                AWAY_LINEUP_DEFENSE to TEXT,
                HOME_LINEUP_MIDFIELD to TEXT,
                AWAY_LINEUP_MIDFIELD to TEXT,
                HOME_LINEUP_FORWARD to TEXT,
                AWAY_LINEUP_FORWARD to TEXT,
                HOME_LINEUP_SUBSTITUTES to TEXT,
                AWAY_LINEUP_SUBSTITUTES to TEXT,
                HOME_FORMATION to TEXT,
                AWAY_FORMATION to TEXT,
                STR_TEAM_BADGE to TEXT,
                HOME_TEAM_ID to TEXT,
                AWAY_TEAM_ID to TEXT)

        db?.createTable(FAVORITE_TEAM_TABLE,true,
                FAVORITE_TEAM_ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                TEAM_ID to TEXT + UNIQUE,
                TEAM_NAME to TEXT,
                TEAM_BADGE to TEXT,
                TEAM_YEAR to TEXT,
                TEAM_STADIUM to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FAVORITE_TABLE, true)
        db.dropTable(FAVORITE_TEAM_TABLE, true)
    }
}
val Context.db: DatabaseOpenHelper
    get() = DatabaseOpenHelper.getInstance(applicationContext)