package com.pau.putrautama.footballschedule.view.fragments.favorite.favoriteTeam


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.pau.putrautama.footballschedule.R
import com.pau.putrautama.footballschedule.adapter.FavoriteTeamAdapter
import com.pau.putrautama.footballschedule.db.FavoriteTeam
import com.pau.putrautama.footballschedule.db.FavoriteTeam.Companion.FAVORITE_TEAM_TABLE
import com.pau.putrautama.footballschedule.db.db
import com.pau.putrautama.footballschedule.util.Key
import com.pau.putrautama.footballschedule.view.detail.detailTeam.DetailTeamActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout


class FavoriteTeamFragment : Fragment(), AnkoComponent<Context> {


    private var favoriteTeam: MutableList<FavoriteTeam> = mutableListOf()
    private lateinit var adapter: FavoriteTeamAdapter
    private lateinit var rvFavoriteTeam: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = FavoriteTeamAdapter(favoriteTeam) {
            startActivity<DetailTeamActivity>(Key.TEAM_ID_KEY to it.teamId)
        }

        rvFavoriteTeam.adapter = adapter
        showFavoriteTeam()

        swipeRefreshLayout.onRefresh {
            favoriteTeam.clear()
            showFavoriteTeam()
        }
    }

    private fun showFavoriteTeam() {
        context?.db?.use {
            swipeRefreshLayout.isRefreshing = false
            val result = select(FAVORITE_TEAM_TABLE)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            favoriteTeam.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = matchParent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(10)
            leftPadding = dip(10)
            rightPadding = dip(10)

            swipeRefreshLayout = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    rvFavoriteTeam = recyclerView {
                        id = R.id.rv_favorite_team
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                }
            }
        }
    }
}



