package com.pau.putrautama.footballschedule.view.fragments.team.player


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.google.gson.Gson
import com.pau.putrautama.footballschedule.R
import com.pau.putrautama.footballschedule.adapter.PlayerAdapter
import com.pau.putrautama.footballschedule.adapter.TeamViewPagerAdapter.Companion.KEY_TEAM_ID_PLAYER
import com.pau.putrautama.footballschedule.api.ApiRepository
import com.pau.putrautama.footballschedule.model.Player
import com.pau.putrautama.footballschedule.util.Key.Companion.TEAM_ID_KEY
import com.pau.putrautama.footballschedule.view.detail.detailPlayer.DetailPlayerActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity

class PlayerFragment : Fragment(),AnkoComponent<Context>, PlayerView {
    private var player: MutableList<Player> = mutableListOf()
    private lateinit var presenter: PlayerPresenter
    private lateinit var adapter: PlayerAdapter
    private lateinit var rvPlayer: RecyclerView
    private lateinit var teamId: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
        }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = PlayerAdapter(player){
            startActivity<DetailPlayerActivity>(
                    TEAM_ID_KEY to  it.idPlayer
            )
        }
        rvPlayer.adapter = adapter

        val bindData = arguments
        teamId = bindData?.getString(KEY_TEAM_ID_PLAYER) ?: "teamId"

        Log.d("isi",teamId)

        val request = ApiRepository()
        val gson = Gson()

        presenter = PlayerPresenter(this, request, gson)
        presenter.getPlayerList(teamId)



    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams(width = matchParent, height = matchParent)
            orientation = LinearLayout.VERTICAL
            backgroundColor = Color.WHITE
            topPadding = dip(10)
            leftPadding = dip(10)
            rightPadding = dip(10)

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    rvPlayer = recyclerView {
                        id = R.id.rv_player
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                }
            }

    }

    override fun showDataLoading() {

    }

    override fun hideDataLoading() {

    }

    override fun showPlayerList(team: List<Player>) {
        player.clear()
        player.addAll(team)
        adapter.notifyDataSetChanged()
    }
}

