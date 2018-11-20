package com.pau.putrautama.footballschedule.view.fragments.match


import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pau.putrautama.footballschedule.R
import com.pau.putrautama.footballschedule.view.fragments.match.nextMatch.NextMatchFragment
import com.pau.putrautama.footballschedule.view.fragments.match.prevMatch.PrevMatchFragment
import com.pau.putrautama.footballschedule.adapter.ViewPagerAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.viewPager


class MatchFragment : Fragment(),AnkoComponent<Context> {


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
        viewPagerAdapter.addFragment(PrevMatchFragment(),resources.getString(R.string.previous_match))
        viewPagerAdapter.addFragment(NextMatchFragment(), resources.getString(R.string.next_match))

        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        relativeLayout{
            lparams(width = matchParent, height = matchParent)

            tabLayout = tabLayout {
                id = R.id.match_tablayout

            }.lparams {
                width = matchParent
                height = wrapContent
            }

            viewPager = viewPager {
                id = R.id.match_pager

            }.lparams {
                width = matchParent
                height = matchParent
                below(R.id.match_tablayout)
            }
        }
    }
}



