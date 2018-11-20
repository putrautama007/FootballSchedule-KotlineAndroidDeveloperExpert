package com.pau.putrautama.footballschedule.view.fragments.favorite


import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pau.putrautama.footballschedule.R
import com.pau.putrautama.footballschedule.view.fragments.favorite.favoriteMatch.FavoriteMatchFragment
import com.pau.putrautama.footballschedule.view.fragments.favorite.favoriteTeam.FavoriteTeamFragment
import com.pau.putrautama.footballschedule.adapter.ViewPagerAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.viewPager


class FavoriteFragment : Fragment(),AnkoComponent<Context> {


    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
        }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewPagerAdapter = ViewPagerAdapter(childFragmentManager)
        viewPagerAdapter.addFragment(FavoriteMatchFragment(),resources.getString(R.string.favorite_match))
        viewPagerAdapter.addFragment(FavoriteTeamFragment(), resources.getString(R.string.favorite_team))

        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun createView(ui: AnkoContext<Context>): View= with(ui) {
        relativeLayout{
            lparams(width = matchParent, height = matchParent)

            tabLayout = tabLayout {
                id = R.id.favorite_tablayout

            }.lparams {
                width = matchParent
                height = wrapContent
            }

            viewPager = viewPager {
                id = R.id.favorite_pager

            }.lparams {
                width = matchParent
                height = matchParent
                below(R.id.favorite_tablayout)
            }
        }
    }
    }
