package com.pau.putrautama.footballschedule.view.main



import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.pau.putrautama.footballschedule.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest{
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testAppBehaviour(){
        Thread.sleep(3000)
        onView(withId(navigation))
                .check(matches(isDisplayed()))
        Thread.sleep(3000)
        onView(withId(navigation_match)).check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withId(navigation_match)).perform(click())
        Thread.sleep(3000)
        onView(withId(rv_prev_match))
                .check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withId(rv_prev_match)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        Thread.sleep(1000)
        onView(withId(add_to_favorite)).perform(click())
        pressBack()
        Thread.sleep(4000)

        onView(withId(rv_prev_match))
                .check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withId(rv_prev_match)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))
        Thread.sleep(1000)
        onView(withId(add_to_favorite)).perform(click())
        pressBack()
        Thread.sleep(4000)

        onView(withId(navigation_favorite)).check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withId(navigation_favorite)).perform(click())
//        onView(withId(rv_favorite_match))
//                .check(matches(isDisplayed()))
        Thread.sleep(4000)
    }
}