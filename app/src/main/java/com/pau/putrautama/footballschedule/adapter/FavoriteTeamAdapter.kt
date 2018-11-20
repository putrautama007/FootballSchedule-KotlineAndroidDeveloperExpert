package com.pau.putrautama.footballschedule.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.pau.putrautama.footballschedule.R
import com.pau.putrautama.footballschedule.R.id.team_badge_favorite
import com.pau.putrautama.footballschedule.R.id.team_name_favorite
import com.pau.putrautama.footballschedule.db.FavoriteTeam
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk15.coroutines.onClick

class FavoriteTeamAdapter(private val favorteTeam : List<FavoriteTeam>, private val listener : (FavoriteTeam) -> Unit)
    : RecyclerView.Adapter<FavoriteTeamViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTeamViewHolder {
        return FavoriteTeamViewHolder(FavoriteTeamUI().createView(AnkoContext.create(parent.context,parent)))
    }

    override fun getItemCount(): Int {
        return favorteTeam.size
    }

    override fun onBindViewHolder(holder: FavoriteTeamViewHolder, position: Int) {
        holder.bindItem(favorteTeam[position], listener)
    }
}

class FavoriteTeamViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    private val teamBadge : ImageView = itemView.find(team_badge_favorite)
    private val teamName : TextView = itemView.find(team_name_favorite)

    fun bindItem(favoriteTeams : FavoriteTeam, listener: (FavoriteTeam) -> Unit){
        Picasso.get().load(favoriteTeams.teamName).into(teamBadge)
        teamName.text = favoriteTeams.teamBadge
        itemView.onClick {
            listener(favoriteTeams)
        }
    }
}

class FavoriteTeamUI : AnkoComponent<ViewGroup>{
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            padding = dip(16)
            orientation = LinearLayout.HORIZONTAL

            imageView {
                id = R.id.team_badge_favorite
            }.lparams{
                height = dip(64)
                width = dip(64)
            }

            textView {
                id = R.id.team_name_favorite
                textSize = 16f
            }.lparams{
                margin = dip(15)
            }

        }
    }

}
