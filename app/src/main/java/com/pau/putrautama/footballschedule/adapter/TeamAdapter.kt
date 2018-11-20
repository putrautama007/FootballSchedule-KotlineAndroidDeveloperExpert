package com.pau.putrautama.footballschedule.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.pau.putrautama.footballschedule.R
import com.pau.putrautama.footballschedule.R.id.team_badge
import com.pau.putrautama.footballschedule.R.id.team_name
import com.pau.putrautama.footballschedule.model.Team
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk15.coroutines.onClick

class TeamAdapter(private val teams: List<Team>, private val listener: (Team) -> Unit)
    : RecyclerView.Adapter<TeamViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)
    }
}

class TeamViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    private val teamBadge : ImageView = itemView.find(team_badge)
    private val teamName : TextView = itemView.find(team_name)

    fun bindItem(team : Team, listener: (Team) -> Unit){
        Picasso.get().load(team.teamBadge).into(teamBadge)
        teamName.text = team.teamName
        itemView.onClick {
            listener(team)
        }
    }
}

class TeamUI : AnkoComponent<ViewGroup>{
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            padding = dip(16)
            orientation = LinearLayout.HORIZONTAL

            imageView {
                id = R.id.team_badge
            }.lparams{
                height = dip(64)
                width = dip(64)
            }

            textView {
                id = R.id.team_name
                textSize = 16f
            }.lparams{
                margin = dip(15)
            }

        }
    }
}
