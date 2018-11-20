package com.pau.putrautama.footballschedule.view.fragments.team.description


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.google.gson.Gson
import com.pau.putrautama.footballschedule.R
import com.pau.putrautama.footballschedule.adapter.TeamViewPagerAdapter.Companion.KEY_TEAM_ID
import com.pau.putrautama.footballschedule.api.ApiRepository
import com.pau.putrautama.footballschedule.model.Team
import com.pau.putrautama.footballschedule.util.Key
import com.pau.putrautama.footballschedule.util.invisible
import com.pau.putrautama.footballschedule.util.visisble
import com.pau.putrautama.footballschedule.view.detail.detailTeam.DetailTeamPresenter
import com.pau.putrautama.footballschedule.view.detail.detailTeam.DetailTeamView
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class DescriptionFragment : Fragment(), AnkoComponent<Context>,DetailTeamView {



    private lateinit var teamDesc: TextView

    private lateinit var presenter: DetailTeamPresenter
    private lateinit var teams: Team

    private lateinit var teamId : String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
        }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bindData = arguments
        teamId = bindData?.getString(KEY_TEAM_ID) ?: "teamId"
        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailTeamPresenter(this,request,gson)
        presenter.getDetailTeam(teamId)


    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            backgroundColor = Color.WHITE

            scrollView {
                isVerticalScrollBarEnabled = false
                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    teamDesc = textView().lparams {
                        topMargin = dip(20)
                        marginStart = dip(10)
                        marginEnd = dip(10)
                    }
                }
            }
        }

    }
    override fun showDataLoading() {

    }

    override fun hideDataLoading() {

    }

    override fun showDetailTeam(team: List<Team>) {
        teams = Team(team.get(0).teamId,
                team.get(0).teamDescription)

        teamDesc.text = team.get(0).teamDescription
    }

}



