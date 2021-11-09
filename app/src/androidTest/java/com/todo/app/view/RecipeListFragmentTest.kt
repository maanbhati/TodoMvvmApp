package com.todo.app.view

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.todo.app.R
import com.todo.app.view.activity.RecipeSearchActivity
import com.todo.app.view.adapter.RecipeListAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class RecipeListFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun test_list_fragment_when_activity_launched_displayed_fragment() {
        mockActivityScenario()

        onView(withId(R.id.listFragment))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_list_fragment_when_launched_displayed_search_view() {
        mockActivityScenario()

        onView(withId(R.id.editSearch))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_list_fragment_when_launched_displayed_recycler_view() {
        mockActivityScenario()

        onView(withId(R.id.rvListItems))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_list_fragment_when_launched_displayed_progressBar() {
        mockActivityScenario()
        onView(withId(R.id.editSearch))
            .perform(clearText(), typeText("Coffee"))
        mockThreadSleep(1000)

        onView(withId(R.id.progressBar))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_list_fragment_when_item_clicked_displayed_detail_fragment() {
        mockSearchQuery()

        onView(withId(R.id.rvListItems)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecipeListAdapter.RecipeListViewHolder>(
                1,
                ViewActions.click()
            )
        )
        onView(withId(R.id.detailFragment))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_list_fragment_when_item_clicked_displayed_detail_fragment_webview() {
        mockSearchQuery()

        onView(withId(R.id.rvListItems)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecipeListAdapter.RecipeListViewHolder>(
                2,
                ViewActions.click()
            )
        )
        onView(withId(R.id.webView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    private fun mockSearchQuery() {
        mockActivityScenario()
        onView(withId(R.id.editSearch))
            .perform(clearText(), typeText("Coffee"))
        mockThreadSleep(3000)
    }

    private fun mockActivityScenario() {
        ActivityScenario.launch(RecipeSearchActivity::class.java)
        mockThreadSleep(1000)
    }

    private fun mockThreadSleep(milliSeconds: Long) {
        try {
            Thread.sleep(milliSeconds)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}