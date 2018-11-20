package com.pau.putrautama.footballschedule.view.detail.detailTeam

import android.app.FragmentManager
import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import com.google.gson.Gson
import com.pau.putrautama.footballschedule.R
import com.pau.putrautama.footballschedule.R.drawable.ic_add_to_favorites
import com.pau.putrautama.footballschedule.R.drawable.ic_added_to_favorites
import com.pau.putrautama.footballschedule.R.id.add_to_favorite
import com.pau.putrautama.footballschedule.adapter.TeamViewPagerAdapter
import com.pau.putrautama.footballschedule.adapter.ViewPagerAdapter
import com.pau.putrautama.footballschedule.api.ApiRepository
import com.pau.putrautama.footballschedule.db.FavoriteTeam
import com.pau.putrautama.footballschedule.db.FavoriteTeam.Companion.FAVORITE_TEAM_TABLE
import com.pau.putrautama.footballschedule.db.FavoriteTeam.Companion.TEAM_BADGE
import com.pau.putrautama.footballschedule.db.FavoriteTeam.Companion.TEAM_ID
import com.pau.putrautama.footballschedule.db.FavoriteTeam.Companion.TEAM_NAME
import com.pau.putrautama.footballschedule.db.FavoriteTeam.Companion.TEAM_STADIUM
import com.pau.putrautama.footballschedule.db.FavoriteTeam.Companion.TEAM_YEAR
import com.pau.putrautama.footballschedule.db.db
import com.pau.putrautama.footballschedule.model.Team
import com.pau.putrautama.footballschedule.util.Key.Companion.TEAM_ID_KEY
import com.pau.putrautama.footballschedule.util.invisible
import com.pau.putrautama.footballschedule.util.visisble
import com.pau.putrautama.footballschedule.view.fragments.match.nextMatch.NextMatchFragment
import com.pau.putrautama.footballschedule.view.fragments.match.prevMatch.PrevMatchFragment
import com.pau.putrautama.footballschedule.view.fragments.team.TeamPresenter
import com.pau.putrautama.footballschedule.view.fragments.team.description.DescriptionFragment
import com.pau.putrautama.footballschedule.view.fragments.team.player.PlayerFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_match.*
import kotlinx.android.synthetic.main.activity_detail_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import java.lang.Exception

class DetailTeamActivity : AppCompatActivity(), DetailTeamView {


    private lateinit var presenter: DetailTeamPresenter
    private lateinit var teams: Team

    private lateinit var viewPagerAdapter: TeamViewPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var teamId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)
        supportActionBar?.title = getString(R.string.detail_team)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewPager = findViewById(R.id.viewpager_detail_team)
        tabLayout = findViewById(R.id.tabs_detail_team)

        teamId = intent.getStringExtra(TEAM_ID_KEY)

        viewPagerAdapter = TeamViewPagerAdapter(teamId, supportFragmentManager, 2)
        viewPager.adapter = viewPagerAdapter

        tabLayout.setupWithViewPager(viewPager)

        favoriteState()
        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailTeamPresenter(this, request, gson)
        presenter.getDetailTeam(teamId)

    }

    override fun showDataLoading() {

    }

    override fun hideDataLoading() {

    }

    override fun showDetailTeam(team: List<Team>) {
        teams = Team(team.get(0).teamId,
                team.get(0).teamName,
                team.get(0).teamBadge,
                team.get(0).teamFormedYear,
                team.get(0).teamStadium)

        tv_team_name.text =  team.get(0).teamName
        tv_team_stadium.text = team.get(0).teamStadium
        tv_team_year.text = team.get(0).teamFormedYear
        Picasso.get().load(team.get(0).teamBadge).into(iv_team)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            android.R.id.home ->{
                finish()
                true
            }
            add_to_favorite ->{
                try {
                    if (isFavorite) removeFavorite() else addFavorite()

                    isFavorite = !isFavorite
                    setFavorite()
                }catch (e : Exception){
                    toast(getString(R.string.wait))
                }
                true
            }
            else ->  super.onOptionsItemSelected(item)
        }

    }

    private fun addFavorite() {
        try {
            db.use {
                insert(FAVORITE_TEAM_TABLE,
                        TEAM_ID to teams.teamBadge,
                        TEAM_NAME to teams.teamName,
                        TEAM_BADGE to teams.teamId,
                        TEAM_YEAR to teams.teamFormedYear,
                        TEAM_STADIUM to teams.teamStadium
                        )
            }
            Snackbar.make(findViewById(R.id.cl_detail),
                    "Added to favorite", Snackbar.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {
            Snackbar.make(findViewById(R.id.cl_detail),
                    "Can't add to favorite", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun favoriteState() {
        db.use {
            val result = select(FAVORITE_TEAM_TABLE)
                    .whereArgs("(TEAM_ID = {teamId})", "teamId" to teamId)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun removeFavorite() {
        try {
            db.use {
                delete(FAVORITE_TEAM_TABLE,
                        "(TEAM_ID = {teamId})",
                        "teamId" to teamId)
            }
            Snackbar.make(findViewById(R.id.cl_detail),
                    "Removed from favorite", Snackbar.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {
            Snackbar.make(findViewById(R.id.cl_detail),
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
