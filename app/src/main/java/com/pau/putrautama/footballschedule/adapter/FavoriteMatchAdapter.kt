package com.pau.putrautama.footballschedule.adapter

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.pau.putrautama.footballschedule.R
import com.pau.putrautama.footballschedule.R.id.*
import com.pau.putrautama.footballschedule.db.FavoriteMatch
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk15.coroutines.onClick

class FavoriteAdapter(private val favoriteMatch: List<FavoriteMatch>, private val listener: (FavoriteMatch) -> Unit)
: RecyclerView.Adapter<FavoriteViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(FavoriteMatchUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int {
        return favoriteMatch.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favoriteMatch[position], listener)
    }
}

class FavoriteViewHolder(itemView :View) : RecyclerView.ViewHolder(itemView) {
    private val favoriteMatchDate : TextView = itemView.find(favorite_match_date)
    private val favoriteHomeTeam : TextView = itemView.find(favorite_home_team)
    private val favoriteAwayTeam : TextView = itemView.find(favorite_away_team)
    private val favoriteMatchScore : TextView = itemView.find(favorite_match_score)

    @SuppressLint("SetTextI18n")
    fun bindItem(favoriteMatch: FavoriteMatch, listener: (FavoriteMatch) -> Unit){
        favoriteMatchDate.text = favoriteMatch.eventDate
        favoriteHomeTeam.text = favoriteMatch.homeTeam
        favoriteAwayTeam.text = favoriteMatch.awayTeam
        if (favoriteMatch.homeScore != null && favoriteMatch.awayScore != null){
            favoriteMatchScore.text = "${favoriteMatch.homeScore} VS ${favoriteMatch.awayScore}"
        }else{
            favoriteMatchScore.text = "VS"
        }
        itemView.onClick {
            listener(favoriteMatch)
        }

    }
}

class FavoriteMatchUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        cardView {
            id = R.id.cv_favorite_match
            lparams(width = matchParent ,height = wrapContent) {
                topMargin = dip(16)
                rightMargin = dip(16)
                leftMargin = dip(16)
            }
            linearLayout {
                orientation = LinearLayout.VERTICAL

                textView {
                    id = R.id.favorite_match_date
                    gravity = Gravity.CENTER
                    textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                }.lparams {
                    width = matchParent
                    height = wrapContent
                }

                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    orientation = LinearLayout.HORIZONTAL

                    textView {
                        id = R.id.favorite_home_team
                        gravity = Gravity.CENTER
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                        weight = 1f
                        topMargin = dip(10)
                    }

                    textView {
                        id = R.id.favorite_match_score
                        gravity = Gravity.CENTER
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                        weight = 1f
                        topMargin = dip(10)

                    }
                    textView {
                        id = R.id.favorite_away_team
                        gravity = Gravity.CENTER
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                        weight = 1f
                        topMargin = dip(10)
                    }
                }

            }
        }
    }
}

