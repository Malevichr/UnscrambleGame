package com.ru.malevich.unscramblegame.game

import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatButton
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not

import org.hamcrest.Matcher

class ButtonUi(
    id: Int,
    @StringRes textResId: Int,
    containerIdMatcher: Matcher<View>,
    containerClassIdMatcher: Matcher<View>
) : AbstractButtonUi(
    onView(
        allOf(
            containerClassIdMatcher,
            containerIdMatcher,
            withId(id),
            withText(textResId),
            isAssignableFrom(AppCompatButton::class.java)
        )
    )
) {
    fun assertVisible() {
        interaction.check(matches(isDisplayed()))
    }

    fun assertNotVisible() {
        interaction.check(matches(not(isDisplayed())))
    }
}

abstract class AbstractButtonUi(
    protected val interaction: ViewInteraction
) {
    fun click() {
        interaction.perform(androidx.test.espresso.action.ViewActions.click())
    }
}