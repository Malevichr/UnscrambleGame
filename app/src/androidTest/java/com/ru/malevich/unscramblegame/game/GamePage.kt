package com.ru.malevich.unscramblegame.game

import android.view.View
import android.widget.LinearLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.ru.malevich.unscramblegame.R
import org.hamcrest.Matcher

class GamePage(scrambledWord: String) {
    private val containerIdMatcher: Matcher<View> = withParent(withId(R.id.rootLayout))
    private val containerClassIdMatcher: Matcher<View> =
        withParent(isAssignableFrom(LinearLayout::class.java))

    private val scrambledWordUi = ScrambledWordUi(
        id = R.id.scrambledWordTextView,
        text = scrambledWord,
        containerIdMatcher = containerIdMatcher,
        containerClassIdMatcher = containerClassIdMatcher
    )
    private val inputTextUi = InputTextUi(
        containerIdMatcher = containerIdMatcher,
        containerClassIdMatcher = containerClassIdMatcher
    )
    private val checkUi = CheckButtonUi(
        id = R.id.checkButton,
        text = R.string.check,
        containerIdMatcher = containerIdMatcher,
        containerClassIdMatcher = containerClassIdMatcher
    )
    private val skipButtonUi = ButtonUi(
        id = R.id.skipButton,
        textResId = R.string.skip,
        containerIdMatcher = containerIdMatcher,
        containerClassIdMatcher = containerClassIdMatcher
    )
    private val nextButtonUi = ButtonUi(
        id = R.id.nextButton,
        textResId = R.string.next,
        containerIdMatcher = containerIdMatcher,
        containerClassIdMatcher = containerClassIdMatcher
    )

    fun assertInitialState() {
        inputTextUi.assertInitialState()
        checkUi.assertNotEnabled()
        skipButtonUi.assertVisible()
        nextButtonUi.assertNotVisible()
        scrambledWordUi.assertTextVisible()
    }

    fun input(text: String) {
        inputTextUi.input(text)
    }

    fun assertInsufficientInputState() {
        scrambledWordUi.assertTextVisible()
        inputTextUi.assertInsufficientInputState()
        checkUi.assertNotEnabled()
        skipButtonUi.assertVisible()
        nextButtonUi.assertNotVisible()
    }

    fun remove(count: Int) {
        inputTextUi.remove(count)
    }

    fun assertSufficientInputState() {
        scrambledWordUi.assertTextVisible()
        inputTextUi.assertSufficientInputState()
        checkUi.assertEnabled()
        skipButtonUi.assertVisible()
        nextButtonUi.assertNotVisible()
    }

    fun clickCheck() {
        checkUi.click()
    }

    fun assertRightAnsweredState() {
        scrambledWordUi.assertTextVisible()
        inputTextUi.assertNotAvailableToInput()
        checkUi.assertRightAnsweredState()
        skipButtonUi.assertNotVisible()
        nextButtonUi.assertVisible()
    }

    fun clickNext() {
        nextButtonUi.click()
    }

    fun assertWrongAnsweredState() {
        scrambledWordUi.assertTextVisible()
        inputTextUi.assertSufficientInputState()
        checkUi.assertWrongAnsweredState()
        skipButtonUi.assertVisible()
        nextButtonUi.assertNotVisible()
    }

    fun clickSkip() {
        skipButtonUi.click()
    }

    fun assertDoesNotExist() {
        onView(containerIdMatcher).check(doesNotExist())
    }

}