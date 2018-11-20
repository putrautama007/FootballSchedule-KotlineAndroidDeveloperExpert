package com.pau.putrautama.footballschedule.view.fragments.favorite.favoriteMatch


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
import com.pau.putrautama.footballschedule.adapter.FavoriteAdapter
import com.pau.putrautama.footballschedule.db.db
import com.pau.putrautama.footballschedule.db.FavoriteMatch
import com.pau.putrautama.footballschedule.db.FavoriteMatch.Companion.FAVORITE_TABLE
import com.pau.putrautama.footballschedule.util.Key.Companion.AWAY_TEAM_ID_KEY
import com.pau.putrautama.footballschedule.util.Key.Companion.EVENT_ID_KEY
import com.pau.putrautama.footballschedule.util.Key.Companion.HOME_TEAM_ID_KEY
import com.pau.putrautama.footballschedule.view.detail.detailMatch.DetailMatchActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FavoriteMatchFragment : Fragment(), AnkoComponent<Context> {

    private var favoriteMatch : MutableList<FavoriteMatch> = mutableListOf()
    private lateinit var adapter: FavoriteAdapter
    private lateinit var rvFavoriteMatch: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FavoriteAdapter(favoriteMatch){
            startActivity<DetailMatchActivity>(
                    HOME_TEAM_ID_KEY to it.homeTeamId,
                    AWAY_TEAM_ID_KEY to it.awayTeamId,
                    EVENT_ID_KEY to it.eventId

            )
        }

        rvFavoriteMatch.adapter = adapter
        showFavoriteMatch()

        swipeRefreshLayout.onRefresh {
            favoriteMatch.clear()
            showFavoriteMatch()
        }
    }

    private fun showFavoriteMatch() {
        context?.db?.use {
            swipeRefreshLayout.isRefreshing = false
            val result = select(FAVORITE_TABLE)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            favoriteMatch.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
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

                    rvFavoriteMatch = recyclerView {
                        id = R.id.rv_favorite_match
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                }
            }
        }
    }




}
