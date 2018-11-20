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
import com.pau.putrautama.footballschedule.model.Event
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk15.coroutines.onClick


class MatchAdapter (private val events: List<Event>, private val listener: (Event) -> Unit)
    : RecyclerView.Adapter<EventViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(EventUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }
}

class EventViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    private val matchDate : TextView = itemView.find(match_date)
    private val homeTeam : TextView = itemView.find(home_team)
    private val awayTeam : TextView = itemView.find(away_team)
    private val matchScore : TextView = itemView.find(match_score)

    @SuppressLint("SetTextI18n")
    fun bindItem(event: Event, listener: (Event) -> Unit){
        matchDate.text = event.eventDate
        homeTeam.text = event.homeTeam
        awayTeam.text = event.awayTeam
        if (event.homeScore != null && event.awayScore != null){
            matchScore.text = "${event.homeScore} VS ${event.awayScore}"
        }else{
            matchScore.text = "VS"
        }
        itemView.onClick {
            listener(event)
        }

    }
}

class EventUI : AnkoComponent<ViewGroup>{
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui){
        cardView {
            id = R.id.cv_match
            lparams(width = matchParent, height = wrapContent) {
                topMargin = dip(16)
                rightMargin = dip(16)
                leftMargin = dip(16)
            }
            linearLayout {
                orientation = LinearLayout.VERTICAL
                padding = dip(16)

                textView {
                    id = R.id.match_date
                    gravity = Gravity.CENTER
                    textColor = ContextCompat.getColor(ctx,R.color.colorPrimary)
                }.lparams{
                    width = matchParent
                    height = wrapContent
                }

                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    orientation = LinearLayout.HORIZONTAL

                    textView {
                        id = R.id.home_team
                        gravity = Gravity.CENTER
                    }.lparams{
                        width = matchParent
                        height = wrapContent
                        weight = 1f
                        topMargin= dip(10)
                    }

                    textView {
                        id = R.id.match_score
                        gravity = Gravity.CENTER
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                        weight = 1f
                        topMargin = dip(10)

                    }
                    textView {
                        id = R.id.away_team
                        gravity = Gravity.CENTER
                    }.lparams{
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

