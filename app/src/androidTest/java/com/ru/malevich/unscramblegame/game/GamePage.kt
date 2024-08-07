package com.ru.malevich.unscramblegame.game

import android.view.View
import android.widget.LinearLayout
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import org.hamcrest.Matcher

class GamePage(scrambledWord: String) {
    private val containerIdMatcher: Matcher<View> = withParent(withId(R.id.rootLayout))
    private val containerClassIdMatcher: Matcher<View> =
        withParent(isAssignableFrom(LinearLayout::class.java))

    private val scrambledWordUi = ScrambledWordUi(
        text = scrambledWord,
        containerIdMatcher = containerIdMatcher,
        containerclassIdMatcher = containerClassIdMatcher
    )
    private val inputTextUi = InoutTextUi(
        containerIdMatcher = containerIdMatcher,
        containerclassIdMatcher = containerClassIdMatcher
    )
    private val checkUi = CheckButtonUi(
        id = R.id.checkButton,
        containerIdMatcher = containerIdMatcher,
        containerclassIdMatcher = containerClassIdMatcher
    )
    private val skipButtonUi = ButtonUi(
        id = R.id.skipButton,
        text = R.string.skip
    )
    private val nextButtonUi = ButtonUi(
        id = R.id.nextButton,
        text = R.string.next
    )

    fun assertInitialState() {
        scrambledWordUi.assertTextVisible()
        inputTextUi.assertInitialState()
        checkUi.assertNotEnabled()
        skipButtonUi.assertVisible()
        nextButtonUi.assertNotVisible()
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

}