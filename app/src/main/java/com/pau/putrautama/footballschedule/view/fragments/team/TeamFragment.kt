package com.pau.putrautama.footballschedule.view.fragments.team


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.gson.Gson
import com.pau.putrautama.footballschedule.R
import com.pau.putrautama.footballschedule.R.array.league
import com.pau.putrautama.footballschedule.adapter.TeamAdapter
import com.pau.putrautama.footballschedule.api.ApiRepository
import com.pau.putrautama.footballschedule.model.Team
import com.pau.putrautama.footballschedule.util.Key.Companion.TEAM_ID_KEY
import com.pau.putrautama.footballschedule.util.invisible
import com.pau.putrautama.footballschedule.util.visisble
import com.pau.putrautama.footballschedule.view.detail.detailTeam.DetailTeamActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout


class TeamFragment : Fragment(),AnkoComponent<Context>, TeamView {


    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamPresenter
    private lateinit var adapter: TeamAdapter
    private lateinit var spinner: Spinner
    private lateinit var rvTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var leagueName: String
    private lateinit var etSearch: EditText
    private lateinit var btnSearch: ImageButton



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
        }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        etSearch = view!!.findViewById(R.id.et_search_team)

        val spinnerItems = resources.getStringArray(league)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        adapter = TeamAdapter(teams) {
            startActivity<DetailTeamActivity>(TEAM_ID_KEY to it.teamId)
        }
        rvTeam.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamPresenter(this, request, gson)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                presenter.getTeamList(leagueName, getString(R.string.empty_input))
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        swipeRefresh.onRefresh {
            presenter.getTeamList(leagueName, getString(R.string.empty_input))
        }
    }

    override fun showDataLoading() {
        progressBar.visisble()
    }

    override fun hideDataLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(team: List<Team>?) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        team?.let {
            teams.addAll(it)
            adapter.notifyDataSetChanged()}
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams(width = matchParent, height = matchParent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(10)
            leftPadding = dip(10)
            rightPadding = dip(10)

            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.HORIZONTAL

                etSearch = editText {
                    singleLine = true
                    id = R.id.et_search_team
                    hint = context.getString(R.string.search_team)
                }.lparams(width = dip(0), height = wrapContent, weight = 5f)

                btnSearch = imageButton {
                    imageResource = R.drawable.ic_search_black_24dp
                    backgroundColor = 80000000
                    onClick {
                        presenter.getTeamList(getString(R.string.empty_input),
                                etSearch.textValue())
                    }
                }.lparams(width = dip(0), height = wrapContent, weight = 1f)

            }

            spinner = spinner()

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)
                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    rvTeam = recyclerView {
                        id = R.id.rv_team
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {

                    }.lparams {
                        centerHorizontally()
                    }
                }
            }
        }
    }
    fun EditText.textValue() = text.toString()

}
