package com.pau.putrautama.footballschedule.view.detail.detailMatch

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.TextView
import com.google.gson.Gson
import com.pau.putrautama.footballschedule.R
import com.pau.putrautama.footballschedule.R.drawable.ic_add_to_favorites
import com.pau.putrautama.footballschedule.R.drawable.ic_added_to_favorites
import com.pau.putrautama.footballschedule.R.id.add_to_favorite
import com.pau.putrautama.footballschedule.R.menu.detail_menu
import com.pau.putrautama.footballschedule.api.ApiRepository
import com.pau.putrautama.footballschedule.db.FavoriteMatch
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
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.EVENT_DATE
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
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.STR_TEAM_BADGE
import com.pau.putrautama.footballschedule.db.db
import com.pau.putrautama.footballschedule.model.Event
import com.pau.putrautama.footballschedule.model.Team
import com.pau.putrautama.footballschedule.util.Key.Companion.AWAY_TEAM_ID_KEY
import com.pau.putrautama.footballschedule.util.Key.Companion.EVENT_ID_KEY
import com.pau.putrautama.footballschedule.util.Key.Companion.HOME_TEAM_ID_KEY
import com.pau.putrautama.footballschedule.util.invisible
import com.pau.putrautama.footballschedule.util.visisble
import com.pau.putrautama.footballschedule.view.fragments.match.MatchView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class DetailMatchActivity : AppCompatActivity(),DetailMatchView, MatchView {


    private lateinit var homeTeamId: String
    private lateinit var awayTeamId : String
    private lateinit var eventId: String

    private lateinit var matchDate: TextView
    private lateinit var progressBar: ProgressBar

    private lateinit var detailTeamBadgePresenter: DetailTeamBadgePresenter
    private lateinit var detailMatchPresenter: DetailMatchPresenter

    private lateinit var homeTeamBadge: Team
    private lateinit var awayTeamBadge: Team
    private lateinit var events: Event

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)

        supportActionBar?.title = getString(R.string.match_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        matchDate = findViewById(R.id.match_date_detail)
        progressBar = findViewById(R.id.progress_bar_detail)

        val intent = intent

        homeTeamId = intent.getStringExtra(HOME_TEAM_ID_KEY)
        awayTeamId = intent.getStringExtra(AWAY_TEAM_ID_KEY)
        eventId = intent.getStringExtra(EVENT_ID_KEY)

        favoriteState()
        val request = ApiRepository()
        val gson = Gson()

        detailTeamBadgePresenter = DetailTeamBadgePresenter(this, request, gson)
        detailMatchPresenter = DetailMatchPresenter(this, request, gson)

        detailTeamBadgePresenter.getTeamBadge(homeTeamId, awayTeamId)
        detailMatchPresenter.getDetailEvent(eventId)
    }



    override fun showDataLoading() {
        progressBar.visisble()
    }

    override fun hideDataLoading() {
        progressBar.invisible()
    }

    override fun showTeambadge(homeTeam: List<Team>?, awayTeam: List<Team>?) {
        homeTeamBadge = Team(homeTeam?.get(0)?.teamBadge)
        awayTeamBadge = Team(awayTeam?.get(0)?.teamBadge)

        Picasso.get().load(homeTeam?.get(0)?.teamBadge).into(iv_home)
        Picasso.get().load(awayTeam?.get(0)?.teamBadge).into(iv_away)
    }

    override fun showEventList(event: List<Event>?) {
        events = Event(event?.get(0)?.eventId,
                event?.get(0)?.homeTeam,
                event?.get(0)?.awayTeam,
                event?.get(0)?.homeScore,
                event?.get(0)?.awayScore,
                event?.get(0)?.eventDate,
                event?.get(0)?.homeLineupGoalkeeper,
                event?.get(0)?.awayLineupGoalkeeper,
                event?.get(0)?.homeGoalDetails,
                event?.get(0)?.awayGoalDetails,
                event?.get(0)?.homeShots,
                event?.get(0)?.awayShots,
                event?.get(0)?.homeLineupDefense,
                event?.get(0)?.awayLineupDefense,
                event?.get(0)?.homeLineupmidfield,
                event?.get(0)?.awayLineupMidfield,
                event?.get(0)?.homeLineupForward,
                event?.get(0)?.awayLineupForward,
                event?.get(0)?.homeLineupSubstitutes,
                event?.get(0)?.awayLineupSubstitutes,
                event?.get(0)?.homeFormation,
                event?.get(0)?.awayFormation,
                event?.get(0)?.teamBadge,
                event?.get(0)?.homeTeamId,
                event?.get(0)?.awayTeamId)


        tv_home_name.text = event?.get(0)?.homeTeam
        tv_home_score_match.text = event?.get(0)?.homeScore
        tv_home_goals.text = event?.get(0)?.homeGoalDetails
        tv_home_goalkeeper.text = event?.get(0)?.homeLineupGoalkeeper
        tv_home_shots.text = event?.get(0)?.homeShots
        tv_home_defense.text = event?.get(0)?.homeLineupDefense
        tv_home_forward.text = event?.get(0)?.homeLineupForward
        tv_home_substitutes.text = event?.get(0)?.homeLineupSubstitutes
        tv_home_midfield.text = event?.get(0)?.homeLineupmidfield

        tv_away_name.text = event?.get(0)?.awayTeam
        tv_away_score_match.text = event?.get(0)?.awayScore
        tv_away_goals.text = event?.get(0)?.awayGoalDetails
        tv_away_goalkeeper.text = event?.get(0)?.awayLineupGoalkeeper
        tv_away_shot.text = event?.get(0)?.awayShots
        tv_away_defense.text = event?.get(0)?.awayLineupDefense
        tv_away_forward.text = event?.get(0)?.awayLineupForward
        tv_away_substitutes.text = event?.get(0)?.awayLineupSubstitutes
        tv_away_midfield.text = event?.get(0)?.awayLineupMidfield

        match_date_detail.text = event?.get(0)?.eventDate
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                try {
                    if (isFavorite) removeFavorite() else addFavorite()

                    isFavorite = !isFavorite
                    setFavorite()
                } catch (e: Exception) {
                    toast(getString(R.string.wait))
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addFavorite() {
        try {
            db.use {
                insert(FAVORITE_TABLE,
                        FavoriteMatch.EVENT_ID to events.eventId,
                        HOME_TEAM to events.homeTeam,
                        AWAY_TEAM to events.awayTeam,
                        HOME_SCORE to events.homeScore,
                        AWAY_SCORE to events.awayScore,
                        EVENT_DATE to events.eventDate,
                        HOME_LINEUP_GOALKEEPER to events.homeLineupGoalkeeper,
                        AWAY_LINEUP_GOALKEEPER to events.awayLineupGoalkeeper,
                        HOME_GOAL_DETAILS to events.homeGoalDetails,
                        AWAY_GOAL_DETAILS to events.awayGoalDetails,
                        HOME_SHOTS to events.homeShots,
                        AWAY_SHOTS to events.awayShots,
                        HOME_LINEUP_DEFENSE to events.homeLineupDefense,
                        AWAY_LINEUP_DEFENSE to events.awayLineupDefense,
                        HOME_LINEUP_MIDFIELD to events.homeLineupmidfield,
                        AWAY_LINEUP_MIDFIELD to events.awayLineupMidfield,
                        HOME_LINEUP_FORWARD to events.homeLineupForward,
                        AWAY_LINEUP_FORWARD to events.awayLineupForward,
                        HOME_LINEUP_SUBSTITUTES to events.homeLineupSubstitutes,
                        AWAY_LINEUP_SUBSTITUTES to events.awayLineupSubstitutes,
                        HOME_FORMATION to events.homeFormation,
                        AWAY_FORMATION to events.awayLineupForward,
                        STR_TEAM_BADGE to events.teamBadge,
                        FavoriteMatch.HOME_TEAM_ID to events.homeTeamId,
                        FavoriteMatch.AWAY_TEAM_ID to events.awayTeamId)
            }
            Snackbar.make(findViewById(R.id.layout_detail),
                    "Added to favorite", Snackbar.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {
            Snackbar.make(findViewById(R.id.layout_detail),
                    "Can't add to favorite", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun favoriteState() {
        db.use {
            val result = select(FAVORITE_TABLE)
                    .whereArgs("(EVENT_ID = {eventId})", "eventId" to eventId)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun removeFavorite() {
        try {
            db.use {
                delete(FAVORITE_TABLE,
                        "(EVENT_ID = {eventId})",
                        "eventId" to eventId)
            }
            Snackbar.make(findViewById(R.id.layout_detail),
                    "Removed from favorite", Snackbar.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {
            Snackbar.make(findViewById(R.id.layout_detail),
                    "Can't remove from favorite", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this,
                    ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this,
                    ic_add_to_favorites)
    }
}
