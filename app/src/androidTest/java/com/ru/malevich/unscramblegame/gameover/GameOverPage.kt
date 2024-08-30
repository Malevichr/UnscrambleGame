package com.ru.malevich.unscramblegame.gameover

import android.view.View
import android.widget.FrameLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.ru.malevich.unscramblegame.R
import com.ru.malevich.unscramblegame.game.ButtonUi
import org.hamcrest.Matcher

class GameOverPage(incorrects: Int, corrects: Int) {

    private val containerIdMatcher: Matcher<View> = withParent(withId(R.id.gameOverContainer))
    private val containerTypeMatcher: Matcher<View> =
        withParent(isAssignableFrom(FrameLayout::class.java))

    private val statsUi = StatsUi(
        incorrects = incorrects,
        corrects = corrects,
        containerIdMatcher = containerIdMatcher,
        containerTypeMatcher = containerTypeMatcher
    )

    private val newGameButton = ButtonUi(
        R.id.newGameButton,
        R.string.newGame,
        containerIdMatcher,
        containerTypeMatcher
    )

    fun assertInitialState() {
        statsUi.assertVisible()
    }

    fun clickNewGame() {
        newGameButton.click()
    }

    fun asserDoesNotExist() {
        onView(containerIdMatcher).check(doesNotExist())
    }


}
