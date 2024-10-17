package com.ru.malevich.unscramblegame.gameover

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.ru.malevich.unscramblegame.R
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

class StatsUi(
    incorrects: Int,
    corrects: Int,
    containerIdMatcher: Matcher<View>,
    containerTypeMatcher: Matcher<View>
) {
    private val interaction: ViewInteraction =
        onView(
            allOf(
                containerIdMatcher,
                containerTypeMatcher,
                withId(R.id.statsTextView),
                withText(
                    "Game over\n" +
                            "Corrects: $corrects\n" +
                            "Incorrects: $incorrects"
                ),
                isAssignableFrom(TextView::class.java)
            )
        )

    fun assertVisible() {
        interaction.check(matches(isDisplayed()))
    }

}
