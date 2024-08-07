package com.ru.malevich.unscramblegame.game

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

class ScrambledWordUi(
    id: Int,
    text: String,
    containerIdMatcher: Matcher<View>,
    containerClassIdMatcher: Matcher<View>
) {
    private val interaction: ViewInteraction = onView(
        allOf(
            containerClassIdMatcher,
            containerIdMatcher,
            withText(text),
            withId(id),
            isAssignableFrom(TextView::class.java)
        )
    )

    fun assertTextVisible() {
        interaction.check(matches(isDisplayed()))
    }

}
