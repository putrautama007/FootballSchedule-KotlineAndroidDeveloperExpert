package com.pau.putrautama.footballschedule.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.pau.putrautama.footballschedule.R
import com.pau.putrautama.footballschedule.R.id.*
import com.pau.putrautama.footballschedule.model.Player
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk15.coroutines.onClick

class PlayerAdapter (private val players : List<Player>, private val listener : (Player) -> Unit)
    :RecyclerView.Adapter<PlayerViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(PlayerUI().createView(AnkoContext.create(parent.context,parent)))
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
       holder.bindItem(players[position],listener)
    }
}

class PlayerUI : AnkoComponent<ViewGroup>{
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui){
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            padding = dip(16)
            orientation = LinearLayout.HORIZONTAL

            imageView {
                id = R.id.player_foto
            }.lparams{
                height = dip(64)
                width = dip(64)
            }

            linearLayout {
                lparams(width = wrapContent, height = wrapContent)
                orientation = LinearLayout.VERTICAL

                textView {
                    id = R.id.player_name
                    textSize = 16f
                }.lparams{
                    marginStart = dip(15)
                }

                textView {
                    id = R.id.palyer_position
                    textSize = 16f
                }.lparams {
                    marginStart = dip(15)
                    topMargin = dip(5)
                }
            }

        }
    }

}

class PlayerViewHolder(itemView :View) : RecyclerView.ViewHolder(itemView) {
    private val playerFoto : ImageView = itemView.find(player_foto)
    private val playerName : TextView = itemView.find(player_name)
    private val playerPosition : TextView = itemView.find(palyer_position)

    fun bindItem(player : Player, listener: (Player) -> Unit){
        Picasso.get().load(player.cutout).into(playerFoto)
        playerName.text = player.player
        playerPosition.text = player.position
        itemView.onClick {
            listener(player)
        }
    }
}
