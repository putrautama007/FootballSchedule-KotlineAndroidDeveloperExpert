package com.pau.putrautama.footballschedule.view.detail.detailPlayer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import com.google.gson.Gson
import com.pau.putrautama.footballschedule.R
import com.pau.putrautama.footballschedule.api.ApiRepository
import com.pau.putrautama.footballschedule.model.Player
import com.pau.putrautama.footballschedule.util.Key.Companion.TEAM_ID_KEY
import com.pau.putrautama.footballschedule.util.invisible
import com.pau.putrautama.footballschedule.util.visisble
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_player.*

class DetailPlayerActivity : AppCompatActivity(),DetailPlayerView {


    private lateinit var progressBar: ProgressBar
    private lateinit var presenter: DetailPlayerPresenter
    private lateinit var plyerId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_player)
        supportActionBar?.title = getString(R.string.player_detail)

        progressBar = findViewById(R.id.progress_bar_player_detail)

        plyerId = intent.getStringExtra(TEAM_ID_KEY)
        val request = ApiRepository()
        val gson = Gson()

        presenter = DetailPlayerPresenter(this, request, gson)
        presenter.getDetailPlayer(plyerId)
    }

    override fun showDataLoading() {
        progressBar.visisble()
    }

    override fun hideDataLoading() {
        progressBar.invisible()
    }

    override fun showDetailPlayer(player: List<Player>) {
        Picasso.get().load(player.get(0).fanart1).into(iv_player_detail)
        tv_player_weight.text = player.get(0).weight
        tv_player_height.text = player.get(0).height
        tv_player_position.text = player.get(0).position
        tv_player_bio_detail.text =player.get(0).descriptionEN
    }
}
