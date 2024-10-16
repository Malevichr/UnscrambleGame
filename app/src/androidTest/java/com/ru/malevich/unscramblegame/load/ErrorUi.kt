package com.ru.malevich.unscramblegame.load

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher

class ErrorUi(
    private val id: Int,
    containerIdMatcher: Matcher<View>,
    containerClassMatcher: Matcher<View>
) {


    private val interaction =
        onView(
            allOf(
                withId(id),
                containerIdMatcher,
                containerClassMatcher,
                isAssignableFrom(TextView::class.java)
            )
        )

    fun assertNotVisible() {
        interaction.check(matches(not(isDisplayed())))
    }

    fun assertVisible() {
        interaction.check(matches(isDisplayed()))
            .check(matches(withText("error")))
    }

    fun waitTillVisible() {
        onView(isRoot()).perform(waitTillDisplayed(id, 4000))
    }

    fun waitTillNotExist() {
        onView(isRoot()).perform(waitTillDoesNotExist(id, 4000))
    }
}
