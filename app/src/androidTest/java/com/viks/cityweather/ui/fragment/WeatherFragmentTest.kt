package com.viks.cityweather.ui.fragment

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.viks.cityweather.R
import com.viks.cityweather.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class WeatherFragmentTest {

    /*
    FragmentScenario doesn't work with Dagger Hilt

    private lateinit var scenario: FragmentScenario<HomeFragment>
    @Before
    fun setup() {
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_CityWeather)
        scenario.moveToState(Lifecycle.State.STARTED)
    }*/

    @get:Rule val hiltRule = HiltAndroidRule(this)

    @Before
    fun setup(){
        hiltRule.inject()
    }


    @Test
    fun testLunchFragmentInHiltContainer(){
        launchFragmentInHiltContainer<WeatherFragment> {

        }
    }

    /**
     * Ensures the forecast view is populated.
     */
    @Test
    fun testRecyclerView() {
        onView(nthChildOf(withId(R.id.tvTime), 0))
            .check(matches(hasDescendant(withText("Tue, 20 Apr"))))

    }


    /**
     * Returns the view at childPosition of a RecyclerView
     *
     * @param parentMatcher view to be matched
     * @param childPosition position of the child
     */
    private fun nthChildOf(
        parentMatcher: Matcher<View>,
        childPosition: Int
    ): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Position is $childPosition")
            }

            public override fun matchesSafely(view: View): Boolean {
                if (view.parent !is ViewGroup) {
                    return parentMatcher.matches(view.parent)
                }
                val group = view.parent as ViewGroup
                return parentMatcher.matches(view.parent) && group.getChildAt(childPosition) == view
            }
        }
    }
}