package com.pau.putrautama.footballschedule.view.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pau.putrautama.footballschedule.R
import com.pau.putrautama.footballschedule.R.id.*
import com.pau.putrautama.footballschedule.view.fragments.favorite.FavoriteFragment
import com.pau.putrautama.footballschedule.view.fragments.match.MatchFragment
import com.pau.putrautama.footballschedule.view.fragments.team.TeamFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                navigation_match -> {
                    loadMatch(savedInstanceState)
                }
                navigation_team -> {
                    loadTeam(savedInstanceState)
                }
                navigation_favorite -> {
                    loadFavorite(savedInstanceState)
                }
            }
            true
        }
        navigation.selectedItemId = navigation_match
    }


    private fun loadMatch(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, MatchFragment()
                            , MatchFragment::class.simpleName)
                    .commit()
        }
    }
    private fun loadTeam(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, TeamFragment()
                            , TeamFragment::class.simpleName)
                    .commit()
        }
    }
    private fun loadFavorite(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, FavoriteFragment()
                            , FavoriteFragment::class.simpleName)
                    .commit()
        }
    }
}
