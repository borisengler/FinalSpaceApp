package com.example.finalfinalspace.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.finalfinalspace.R
import com.example.finalfinalspace.data.db.models.EpisodesInfo
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var scenario: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)
    val LIST_ITEM = 5
    val episode: EpisodesInfo = EpisodesInfo(
        1,
        "Chapter 1",
        "02/15/2018",
        "Mike Roberts",
        "Olan Rogers",
        "http://finalspaceapi.com/episode/image/chapter1.jpg"
    )

    @Test
    fun testActivityVisible() {
        onView(withId(R.id.activityMain))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testRecyclerViewVisible() {
        onView(withId(R.id.episodes_recycler_view))
            .check(matches(isDisplayed()))
    }

}