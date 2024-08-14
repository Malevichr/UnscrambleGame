package com.ru.malevich.unscramblegame.game

import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isClickable
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isNotClickable
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.ru.malevich.unscramblegame.ButtonColorMatcher
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

class CheckButtonUi(
    id: Int,
    text: Int,
    containerIdMatcher: Matcher<View>,
    containerClassIdMatcher: Matcher<View>
) : AbstractButtonUi(
    onView(
        allOf(
            containerClassIdMatcher,
            containerIdMatcher,
            withId(id),
            withText(text),
            isAssignableFrom(AppCompatButton::class.java)
        )
    )
) {
    fun assertNotEnabled() {
        interaction.check(matches(isNotEnabled()))
    }

    fun assertEnabled() {
        interaction.check(matches(isEnabled()))
            .check(matches(isClickable()))
    }

    fun assertRightAnsweredState() {
        interaction.check(matches(isEnabled()))
            .check(matches(isNotClickable()))
            .check(matches(ButtonColorMatcher("#36FF17")))
    }

    fun assertWrongAnsweredState() {
        interaction.check(matches(isEnabled()))
            .check(matches(isNotClickable()))
            .check(matches(ButtonColorMatcher("#FF3232")))
    }

}
