package com.pau.putrautama.footballschedule.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.pau.putrautama.footballschedule.view.fragments.team.description.DescriptionFragment
import com.pau.putrautama.footballschedule.view.fragments.team.player.PlayerFragment

class TeamViewPagerAdapter(private val teamId : String, fragmentManager: FragmentManager, private var tabCount : Int)
    : FragmentPagerAdapter(fragmentManager){
    override fun getItem(position: Int): Fragment {
        return when (position){
            0 ->{
                descriptionTeam(teamId)
            }1 ->{
                teamPlayer(teamId)
            }else ->
                return PlayerFragment()
        }
    }

    override fun getCount(): Int {
        return tabCount
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Overview"
            else -> return "Player"
        }
    }

    companion object {
        const val KEY_TEAM_ID = "TEAM_ID"
        const val KEY_TEAM_ID_PLAYER = "TEAM_ID_PLAYER"
        fun descriptionTeam(teamId: String): DescriptionFragment {
            val bindData = Bundle()
            bindData.putString(KEY_TEAM_ID, teamId)

            val desciptionFragment = DescriptionFragment()
            desciptionFragment.arguments = bindData
            return desciptionFragment
        }

        fun teamPlayer(teamId: String): PlayerFragment {
            val bindData = Bundle()
            bindData.putString(KEY_TEAM_ID_PLAYER, teamId)

            val playerFragment = PlayerFragment()
            playerFragment.arguments = bindData
            return playerFragment
        }
    }
}