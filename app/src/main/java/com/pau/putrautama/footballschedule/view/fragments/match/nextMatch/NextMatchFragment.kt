package com.pau.putrautama.footballschedule.view.fragments.match.nextMatch


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
import com.pau.putrautama.footballschedule.R.id.et_search_next
import com.pau.putrautama.footballschedule.R.string.*
import com.pau.putrautama.footballschedule.adapter.MatchAdapter
import com.pau.putrautama.footballschedule.api.ApiRepository
import com.pau.putrautama.footballschedule.model.Event
import com.pau.putrautama.footballschedule.util.Key.Companion.AWAY_TEAM_ID_KEY
import com.pau.putrautama.footballschedule.util.Key.Companion.EVENT_ID_KEY
import com.pau.putrautama.footballschedule.util.Key.Companion.HOME_TEAM_ID_KEY
import com.pau.putrautama.footballschedule.util.invisible
import com.pau.putrautama.footballschedule.util.visisble
import com.pau.putrautama.footballschedule.view.detail.detailMatch.DetailMatchActivity
import com.pau.putrautama.footballschedule.view.fragments.match.MatchView
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout


class NextMatchFragment : Fragment() , AnkoComponent<Context> , MatchView {

    private var events : MutableList<Event> = mutableListOf()
    private lateinit var presenter: NextMatchPresenter
    private lateinit var adapter: MatchAdapter
    private lateinit var rvNextMatch: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var etSearch: EditText
    private lateinit var btnSearch: ImageButton
    private lateinit var spinner: Spinner

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        etSearch = view!!.findViewById(et_search_next)

        adapter = MatchAdapter(events){
            startActivity<DetailMatchActivity>(
                    HOME_TEAM_ID_KEY to it.homeTeamId,
                    AWAY_TEAM_ID_KEY to it.awayTeamId,
                    EVENT_ID_KEY to it.eventId
            )
        }
        rvNextMatch.adapter = adapter

        val spinnerItemsLeague = resources.getStringArray(league)
        val spinnerAdapterLeague = ArrayAdapter(ctx, android.support.design.R.layout.support_simple_spinner_dropdown_item, spinnerItemsLeague)
        spinner.adapter = spinnerAdapterLeague

        val request = ApiRepository()
        val gson = Gson()
        presenter = NextMatchPresenter(this,request, gson)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (spinner.selectedItem ==  getString(english_pemier_league) ){
                    presenter.getNextEventList(getString(english_pemier_league_match), getString(empty_input))

                    swipeRefreshLayout.onRefresh {
                        presenter.getNextEventList(getString(english_pemier_league_match), getString(empty_input))
                    }
                }
                if (spinner.selectedItem ==  getString(english_league_championship) ){
                    presenter.getNextEventList(getString(english_league_championship_match), getString(empty_input))

                    swipeRefreshLayout.onRefresh {
                        presenter.getNextEventList(getString(english_league_championship_match), getString(empty_input))
                    }
                }
                if (spinner.selectedItem ==  getString(german_bundes_liga) ){
                    presenter.getNextEventList(getString(german_bundes_liga_match), getString(empty_input))

                    swipeRefreshLayout.onRefresh {
                        presenter.getNextEventList(getString(german_bundes_liga_match), getString(empty_input))
                    }
                }

                if (spinner.selectedItem ==  getString(italian_serie_A) ){
                    presenter.getNextEventList(getString(italian_serie_A_match), getString(empty_input))

                    swipeRefreshLayout.onRefresh {
                        presenter.getNextEventList(getString(italian_serie_A_match), getString(empty_input))
                    }
                }
                if (spinner.selectedItem ==  getString(french_ligue_1) ){
                    presenter.getNextEventList(getString(french_ligue_1_match), getString(empty_input))

                    swipeRefreshLayout.onRefresh {
                        presenter.getNextEventList(getString(french_ligue_1_match), getString(empty_input))
                    }
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
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

            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.HORIZONTAL

                etSearch = editText {
                    singleLine = true
                    id = R.id.et_search_next
                    hint = context.getString(search_match)
                }.lparams(width = dip(0), height = wrapContent, weight = 5f)

                btnSearch = imageButton {
                    imageResource = R.drawable.ic_search_black_24dp
                    backgroundColor = 80000000
                    onClick {
                        presenter.getNextEventList(getString(R.string.empty_input),
                                etSearch.textValue())
                    }
                }.lparams(width = dip(0), height = wrapContent, weight = 1f)

            }

            spinner = spinner()

            swipeRefreshLayout = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)
                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    rvNextMatch = recyclerView {
                        id = R.id.rv_next_match
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

    override fun showDataLoading() {
        progressBar.visisble()
    }

    override fun hideDataLoading() {
        progressBar.invisible()
    }

    override fun showEventList(event: List<Event>?) {
        swipeRefreshLayout.isRefreshing = false
        events.clear()
        event?.let {
            events.addAll(event)
            adapter.notifyDataSetChanged()
        }

    }

}
