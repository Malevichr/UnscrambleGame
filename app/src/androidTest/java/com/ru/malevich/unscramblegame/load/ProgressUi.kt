package com.ru.malevich.unscramblegame.load

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher

class ProgressUi(
    id: Int,
    containerIdMatcher: Matcher<View>,
    containerClassMatcher: Matcher<View>
) {


    private val interaction =
        onView(
            allOf(
                withId(id),
                containerIdMatcher,
                containerClassMatcher
            )
        )

    fun assertVisible() {
        interaction.check(matches(isDisplayed()))
    }

    fun assertNotVisible() {
        interaction.check(matches(not(isDisplayed())))
    }
}
